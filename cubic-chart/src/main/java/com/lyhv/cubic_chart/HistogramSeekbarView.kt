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
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.addListener
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
    private lateinit var histogram: LineChart
    var lastRealMax = 1.0
    var lastRealMin = 0.0

    /* access modifiers changed from: private */
    private var previousDataSet: LineDataSet? = null

    private var activeGraphFillDrawable: Drawable = AppCompatResources.getDrawable(
        context,
        R.drawable.histogram_fill_gradient
    )!!
    private lateinit var graphInactiveFillRenderer: GraphInactiveFillRenderer
    private val graphValueTextSize = 0
    private val inactiveGraphColor = Color.parseColor("#B5DCFA")
    private var lastSeekbarMaxValue = 1.0
    private var lastSeekbarMinValue = 0.0

    //  private RangeSeekBar noListingsDisabledDummySeekbar;
    private val viewPortLeftOffset = 0
    private val viewPortRightOffset = 0

    constructor(context: Context?) : super(context) {
        initializeHistogram()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initializeHistogram()
    }

    private fun initializeHistogram() {
        View.inflate(context, R.layout.histogram_seak_bar_view, this)
        histogram = findViewById(R.id.line_chart)
        histogram.data = LineData()
        histogram.setTouchEnabled(false)
        histogram.description = null
        histogram.setDrawGridBackground(false)
        histogram.setDrawBorders(false)
        histogram.setDrawMarkers(false)
        histogram.legend.isEnabled = false
        histogram.setNoDataText(null)
        histogram.setHardwareAccelerationEnabled(true)
        histogram.axisLeft.setDrawGridLines(false)
        histogram.axisLeft.setDrawAxisLine(false)
        histogram.axisLeft.setDrawLabels(false)
        histogram.axisLeft.spaceTop = 30.0f
        histogram.axisLeft.axisMinimum = 0.0f
        histogram.axisRight.isEnabled = false
        histogram.xAxis.setDrawGridLines(false)
        histogram.xAxis.setDrawAxisLine(false)
        histogram.xAxis.setDrawLabels(false)
        histogram.setViewPortOffsets(
            viewPortLeftOffset.toFloat(),
            0.0f,
            viewPortRightOffset.toFloat(),
            0.0f
        )
        graphInactiveFillRenderer = GraphInactiveFillRenderer(
            histogram,
            histogram.animator,
            histogram.viewPortHandler
        ).apply {
            inactiveColor = inactiveGraphColor
        }
        histogram.renderer = graphInactiveFillRenderer

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
            histogram.invalidate()
            // TODO update seak bar here
            return
        }
        var lineDataSet =
            histogram.lineData.getDataSetByLabel(DATA_SET_LABEL, false) as? LineDataSet
        if (lineDataSet == null) {
            lineDataSet = LineDataSet(null, DATA_SET_LABEL)
            lineDataSet.color = -1
            lineDataSet.setDrawFilled(true)
            lineDataSet.fillDrawable = activeGraphFillDrawable
            lineDataSet.setDrawIcons(false)
            lineDataSet.isHighlightEnabled = false
            lineDataSet.setDrawHighlightIndicators(false)
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawCircleHole(false)
            lineDataSet.lineWidth = 0.0f
            lineDataSet.highlightLineWidth = 0.0f
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            lineDataSet.valueTextSize = graphValueTextSize.toFloat()
            lineDataSet.valueFormatter = CHART_VALUE_FORMATER
            lineDataSet.setDrawValues(z)
            histogram.data.addDataSet(lineDataSet)
        }
        if (this.previousDataSet == null) {
            this.previousDataSet = lineDataSet
        }
        val fArr = FloatArray(entries.size)

        entries.forEachIndexed { index, entry ->
            val lineDataSet2 = this.previousDataSet
            fArr[index] =
                if (lineDataSet2 == null || lineDataSet2.entryCount <= 0) 0.0f else previousDataSet?.getEntryForXValue(
                    entry.x,
                    entry.y
                )!!.y
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
        logger<HistogramSeekbarView>().debug("${fArr[1]}")
        histogramTransitionAnimator.removeAllListeners()
        histogramTransitionAnimator.addListener(
            onEnd = {
                logger<HistogramSeekbarView>().debug("doOnEnd")
                previousDataSet = lineDataSet
            },
            onCancel = {
                logger<HistogramSeekbarView>().debug("doOnCancel")
                previousDataSet = lineDataSet
            }
        )
        histogramTransitionAnimator.addUpdateListener {
            updateChart(lineDataSet, entries, fArr, histogram.lineData, it)
        }

        if (this.histogramTransitionAnimator.isRunning) {
            this.histogramTransitionAnimator.cancel()
        }
        histogramTransitionAnimator.start()
    }

    @SuppressLint("RestrictedApi")
    private fun updateChart(
        lineDataSet: LineDataSet,
        list: List<Entry>,
        fArr: FloatArray,
        lineData: LineData,
        valueAnimator: ValueAnimator
    ) {
        val animatedFraction = valueAnimator.animatedFraction
        val newEntrys = arrayListOf<Entry>()
        newEntrys.addAll(list)
        lineDataSet.clear()
        val size = newEntrys.size
        for (i in 0 until size) {
            val entry = newEntrys[i]
            lineDataSet.addEntry(
                Entry(
                    entry.x,
                    AnimationUtils.lerp(
                        fArr[i],
                        entry.y,
                        animatedFraction
                    )
                )
            )
            logger<HistogramSeekbarView>().debug(
                "${Entry(
                    entry.x,
                    AnimationUtils.lerp(
                        fArr[i],
                        entry.y,
                        animatedFraction
                    )
                )}"
            )
        }
        lineData.notifyDataChanged()
        graphInactiveFillRenderer.setForceInactive(false)
        histogram.notifyDataSetChanged()
        histogram.invalidate()
    }

    fun updateGraphInactiveFill(d: Double, d2: Double) {
        graphInactiveFillRenderer.activeMinXBound = d.toFloat()
        graphInactiveFillRenderer.activeMaxXBound = d2.toFloat()
        histogram.invalidate()
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

    private object CHART_VALUE_FORMATER : ValueFormatter() {
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

