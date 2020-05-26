package com.lyhv.cubic_chart

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.addListener
import androidx.core.math.MathUtils
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler
import com.google.android.material.animation.AnimationUtils
import kotlinx.android.synthetic.main.histogram_seak_bar_view.view.slider
import java.text.NumberFormat

class HistogramSeekbarView : LinearLayout {
    private val histogramTransitionAnimator =
        ValueAnimator.ofFloat(0.0f, 1.0f).apply {
            interpolator = ANIMATION_INTERPOLATOR
        }
    private lateinit var histogramChart: LineChart
    var lastRealMax = 1.0
    var lastRealMin = 0.0
    private var previousDataSet: LineDataSet? = null

    private val defaultColor = Color.parseColor("#B5DCFA")
    private val defaultGraphFillDrawable = AppCompatResources.getDrawable(
        context,
        R.drawable.histogram_fill_gradient
    )!!
    private var activeGraphFillDrawable: Drawable = defaultGraphFillDrawable
    private lateinit var graphInactiveFillRenderer: GraphInactiveFillRenderer
    private val graphValueTextSize = 0
    private var inactiveGraphColor = defaultColor
    private var lastSeekbarMaxValue = 1.0
    private var lastSeekbarMinValue = 0.0
    private var viewPortLeftOffset = 0
    private var viewPortRightOffset = 0

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        initializeAttributes(context, attrs)
        inflate(context, R.layout.histogram_seak_bar_view, this)
        initializeHistogram()
    }

    private fun initializeAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HistogramSeekbarView)
            inactiveGraphColor = typedArray.getColor(
                R.styleable.HistogramSeekbarView_inactiveGraphColor
                , defaultColor
            )
            activeGraphFillDrawable =
                typedArray.getDrawable(R.styleable.HistogramSeekbarView_activeGraphFillDrawable)
                    ?: defaultGraphFillDrawable
            viewPortLeftOffset = typedArray.getDimensionPixelSize(
                R.styleable.HistogramSeekbarView_viewPortLeftOffset,
                0
            )
            viewPortRightOffset = typedArray.getDimensionPixelSize(
                R.styleable.HistogramSeekbarView_viewPortRightOffset,
                0
            )
            logger<HistogramSeekbarView>().debug("aaa $viewPortRightOffset")
            typedArray.recycle()
        }
    }

    private fun initializeHistogram() {
        histogramChart = findViewById(R.id.line_chart)
        histogramChart.apply {
            data = LineData()
            setTouchEnabled(false)
            description = null
            setDrawGridBackground(false)
            setDrawBorders(false)
            setDrawMarkers(false)
            legend.isEnabled = false
            setNoDataText(null)
            setHardwareAccelerationEnabled(true)
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.setDrawLabels(false)
            axisLeft.spaceTop = 30.0f
            axisLeft.axisMinimum = 0.0f
            axisRight.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.setDrawLabels(false)
            setViewPortOffsets(
                viewPortLeftOffset.toFloat(),
                0.0f,
                viewPortRightOffset.toFloat(),
                0.0f
            )
            graphInactiveFillRenderer = GraphInactiveFillRenderer(
                histogramChart,
                histogramChart.animator,
                histogramChart.viewPortHandler
            ).apply {
                inactiveColor = inactiveGraphColor
            }
            histogramChart.renderer = graphInactiveFillRenderer
        }
        slider.addOnChangeListener { slider, value, fromUser ->
            updateProgressSeekBar(0.0, value.toDouble())
        }
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (histogramTransitionAnimator.isRunning) {
            histogramTransitionAnimator.cancel()
        }
    }

    fun updateGraph(
        entries: List<Entry>,
        z: Boolean
    ) {
        if (entries.isEmpty()) {
            graphInactiveFillRenderer.setForceInactive(true)
            histogramChart.invalidate()
            // TODO update seak bar here
            return
        }
        var lineDataSet =
            histogramChart.lineData.getDataSetByLabel(DATA_SET_LABEL, false) as? LineDataSet
        if (lineDataSet == null) {
            lineDataSet = LineDataSet(null, DATA_SET_LABEL).apply {
                color = -1
                setDrawFilled(true)
                fillDrawable = activeGraphFillDrawable
                setDrawIcons(false)
                isHighlightEnabled = false
                setDrawHighlightIndicators(false)
                setDrawCircles(false)
                setDrawCircleHole(false)
                lineWidth = 0.0f
                highlightLineWidth = 0.0f
                mode = LineDataSet.Mode.CUBIC_BEZIER
                valueTextSize = graphValueTextSize.toFloat()
                valueFormatter = VALUEFORMATER
                setDrawValues(z)
            }
            histogramChart.data.addDataSet(lineDataSet)
        }

        if (this.previousDataSet == null) {
            this.previousDataSet = lineDataSet
        }

        val newFarr = entries.map {
            val dataSet = this.previousDataSet
            val invalidDataSet = dataSet == null || dataSet.entryCount <= 0
            if (invalidDataSet) {
                0.0f
            } else {
                dataSet!!.getEntryForXValue(
                    it.x,
                    it.y
                ).y
            }
        }
        histogramTransitionAnimator.removeAllListeners()
        histogramTransitionAnimator.addListener(
            onEnd = {
                previousDataSet = lineDataSet
            },
            onCancel = {
                previousDataSet = lineDataSet
            }
        )
        histogramTransitionAnimator.addUpdateListener {
            onAnimationUpdateChart(lineDataSet, entries, newFarr, histogramChart.lineData, it)
        }

        if (histogramTransitionAnimator.isRunning) {
            histogramTransitionAnimator.cancel()
        }
        histogramTransitionAnimator.start()
    }

    @SuppressLint("RestrictedApi")
    private fun onAnimationUpdateChart(
        lineDataSet: LineDataSet,
        entries: List<Entry>,
        fArr: List<Float>,
        lineData: LineData,
        valueAnimator: ValueAnimator
    ) {
        val animatedFraction = valueAnimator.animatedFraction
        lineDataSet.clear()
        entries.forEachIndexed { index, entry ->
            lineDataSet.addEntry(
                Entry(
                    entry.x,
                    AnimationUtils.lerp(
                        fArr[index],
                        entry.y,
                        animatedFraction
                    )
                )
            )
        }
        lineData.notifyDataChanged()
        graphInactiveFillRenderer.setForceInactive(false)
        histogramChart.notifyDataSetChanged()
        histogramChart.invalidate()
    }

    fun updateGraphInactiveFill(d: Double, d2: Double) {
        graphInactiveFillRenderer.activeMinXBound = d.toFloat()
        graphInactiveFillRenderer.activeMaxXBound = d2.toFloat()
        histogramChart.invalidate()
    }

    fun updateProgressSeekBar(
        d: Double,
        d2: Double
    ) {
        updateGraphInactiveFill(d, d2)
        var doubleValue = d
        if (lastSeekbarMinValue == d) {
            doubleValue = lastRealMin
        }
        var doubleValue2 = d2
        if (lastSeekbarMaxValue == d2) {
            doubleValue2 = lastRealMax
        }
        lastSeekbarMinValue = d
        lastSeekbarMaxValue = d2
    }

    /**
     * Handler
     */
    internal class GraphInactiveFillRenderer(
        lineDataProvider: LineDataProvider,
        chartAnimator: ChartAnimator,
        viewPortHandler: ViewPortHandler
    ) : LineChartRenderer(lineDataProvider, chartAnimator, viewPortHandler) {
        var activeMaxXBound = 0f
        var activeMinXBound = 0f
        var inactiveColor = 0
        private var forceInactive = false

        fun setForceInactive(z: Boolean) {
            forceInactive = z
            if (z) {
                activeMinXBound = 0.0f
                activeMaxXBound = 1.0f
            }
        }

        public override fun drawFilledPath(
            canvas: Canvas,
            path: Path,
            drawable: Drawable
        ) {
            val iLineDataSet = mChart.lineData.getDataSetByLabel(DATA_SET_LABEL, false)
            val save = canvas.save()
            canvas.clipPath(path)
            canvas.drawColor(inactiveColor)
            if (!forceInactive) {
                val transformer = mChart.getTransformer(iLineDataSet.axisDependency)
                drawable.setBounds(
                    transformer.getPixelForValues(
                        activeMinXBound,
                        0.0f
                    ).x.toInt(),
                    mViewPortHandler.contentTop().toInt(),
                    transformer.getPixelForValues(activeMaxXBound, 0.0f).x.toInt(),
                    mViewPortHandler.contentBottom().toInt()
                )
                drawable.draw(canvas)
            }
            canvas.restoreToCount(save)
        }
    }

    companion object {
        private const val DATA_SET_LABEL = "HistogramSeekbarView"
        private val ANIMATION_INTERPOLATOR: TimeInterpolator =
            Easing.EaseInOutSine
        private val INTEGER_FORMAT =
            NumberFormat.getIntegerInstance()
    }

    private object VALUEFORMATER : ValueFormatter() {
        override fun getFormattedValue(
            value: Float,
            entry: Entry?,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler?
        ): String {
            return INTEGER_FORMAT.format(value)
        }
    }

}

