package com.lyhv.cubic_chart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.animation.AnimationUtils;

import java.text.NumberFormat;
import java.util.List;

public class HistogramSeekbarView extends LinearLayout {
    private static final TimeInterpolator ANIMATION_INTERPOLATOR = Easing.EaseInOutSine;
    private static final NumberFormat INTEGER_FORMAT = NumberFormat.getIntegerInstance();
    private final ValueAnimator histogramTransitionAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
    public TextView debugInfo;
    public LineChart histogram;
    public double lastRealMax = 1.0d;
    public double lastRealMin = 0.0d;
    /* access modifiers changed from: private */
    public LineDataSet previousDataSet;
    //  public Rang<Double> rangeSeekbar;
    private Drawable activeGraphFillDrawable;
    // private IValueFormatter debugGraphFormatter = $$Lambda$HistogramSeekbarView$jMW_yJHc2oIkaWKYTjeleCHrp3c.INSTANCE;
    private Drawable graphGradientDrawable;
    private GraphInactiveFillRenderer graphInactiveFillRenderer;
    private int graphValueTextSize;
    private int inactiveGraphColor;
    private double lastSeekbarMaxValue = 1.0d;
    private double lastSeekbarMinValue = 0.0d;
    //  private RangeSeekBar noListingsDisabledDummySeekbar;
    private int viewPortLeftOffset;
    private int viewPortRightOffset;

    public HistogramSeekbarView(Context context) {
        super(context);
        initializeHistogram();
    }

    public HistogramSeekbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeHistogram();
    }

    private void initializeHistogram() {
        this.histogram.setData(new LineData());
        this.histogram.setTouchEnabled(false);
        this.histogram.setDescription(null);
        this.histogram.setDrawGridBackground(false);
        this.histogram.setDrawBorders(false);
        this.histogram.setDrawMarkers(false);
        this.histogram.getLegend().setEnabled(false);
        this.histogram.setNoDataText(null);
        this.histogram.setHardwareAccelerationEnabled(true);
        this.histogram.getAxisLeft().setDrawGridLines(false);
        this.histogram.getAxisLeft().setDrawAxisLine(false);
        this.histogram.getAxisLeft().setDrawLabels(false);
        this.histogram.getAxisLeft().setSpaceTop(30.0f);
        this.histogram.getAxisLeft().setAxisMinimum(0.0f);
        this.histogram.getAxisRight().setEnabled(false);
        this.histogram.getXAxis().setDrawGridLines(false);
        this.histogram.getXAxis().setDrawAxisLine(false);
        this.histogram.getXAxis().setDrawLabels(false);
        this.histogram.setViewPortOffsets((float) this.viewPortLeftOffset, 0.0f, (float) this.viewPortRightOffset, 0.0f);
        LineChart lineChart = this.histogram;
        GraphInactiveFillRenderer graphInactiveFillRenderer2 = new GraphInactiveFillRenderer(lineChart, lineChart.getAnimator(), this.histogram.getViewPortHandler());
        this.graphInactiveFillRenderer = graphInactiveFillRenderer2;
        graphInactiveFillRenderer2.inactiveColor = this.inactiveGraphColor;
        this.histogram.setRenderer(this.graphInactiveFillRenderer);
        Drawable drawable = this.activeGraphFillDrawable;
        if (drawable == null) {
            drawable = AppCompatResources.getDrawable(getContext(), R.color.color_fill_cubic);
        }
        this.graphGradientDrawable = drawable;
        this.histogramTransitionAnimator.setInterpolator(ANIMATION_INTERPOLATOR);
        this.histogramTransitionAnimator.setDuration(300);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.histogramTransitionAnimator.isRunning()) {
            this.histogramTransitionAnimator.cancel();
        }
    }

    public void setSeekbarEnabled(boolean z) {
        //  this.rangeSeekbar.setEnabled(z);
    }

    public final void updateGraph(List<Entry> list, boolean z) {
        if (list.isEmpty()) {
//            this.graphInactiveFillRenderer.setForceInactive(true);
//            this.histogram.invalidate();
//            this.noListingsDisabledDummySeekbar.setVisibility(0);
//            this.rangeSeekbar.setVisibility(8);
//            RangeSeekBar<Double> rangeSeekBar = this.rangeSeekbar;
//            rangeSeekBar.setSelectedMinValue(rangeSeekBar.absoluteMinValue);
//            rangeSeekBar.setSelectedMaxValue(rangeSeekBar.absoluteMaxValue);
            return;
        }
//        this.noListingsDisabledDummySeekbar.setVisibility(8);
//        this.rangeSeekbar.setVisibility(0);
        LineData lineData = this.histogram.getLineData();
        String str = "com.seatgeek.pricegraphLABEL_DATA";
        LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByLabel(str, false);
        if (lineDataSet == null) {
            lineDataSet = new LineDataSet(null, str);
            lineDataSet.setColor(-1);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(this.graphGradientDrawable);
            lineDataSet.setDrawIcons(false);
            lineDataSet.setHighlightEnabled(false);
            lineDataSet.setDrawHighlightIndicators(false);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setLineWidth(0.0f);
            lineDataSet.setHighlightLineWidth(0.0f);
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setValueTextSize((float) this.graphValueTextSize);
            //lineDataSet.setValueFormatter(this.debugGraphFormatter);
            lineData.addDataSet(lineDataSet);
        }
        lineDataSet.setDrawValues(z);
        float[] fArr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            LineDataSet lineDataSet2 = this.previousDataSet;
            fArr[i] = (lineDataSet2 == null || lineDataSet2.getEntryCount() <= 0) ? 0.0f : this.previousDataSet.getEntryForXValue(entry.getX(), entry.getY()).getY();
        }
        this.histogramTransitionAnimator.removeAllListeners();
        ValueAnimator valueAnimator = this.histogramTransitionAnimator;
        float[] r4 = new ValueAnimator.AnimatorUpdateListener(lineDataSet, list, fArr, lineData) {
            private final /* synthetic */ LineDataSet f$1;
            private final /* synthetic */ List f$2;
            private final /* synthetic */ float[] f$3;
            private final /* synthetic */ LineData f$4;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                HistogramSeekbarView.this.lambda$updateGraph$2$HistogramSeekbarView(this.f$1, this.f$2, this.f$3, this.f$4, valueAnimator);
            }
        };
        valueAnimator.addUpdateListener(r4);
        this.histogramTransitionAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                HistogramSeekbarView.this.previousDataSet = lineDataSet;
            }

            public final void onAnimationCancel(Animator animator) {
                HistogramSeekbarView.this.previousDataSet = lineDataSet;
            }
        });
        if (this.histogramTransitionAnimator.isRunning()) {
            this.histogramTransitionAnimator.cancel();
        }
        this.histogramTransitionAnimator.start();
    }

    @SuppressLint("RestrictedApi")
    public void lambda$updateGraph$2$HistogramSeekbarView(LineDataSet lineDataSet, List list, float[] fArr, LineData lineData, ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        lineDataSet.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Entry entry = (Entry) list.get(i);
            lineDataSet.addEntry(new Entry(entry.getX(), AnimationUtils.lerp(fArr[i], entry.getY(), animatedFraction)));
        }
        lineData.notifyDataChanged();
        this.graphInactiveFillRenderer.setForceInactive(false);
        this.histogram.notifyDataSetChanged();
        this.histogram.invalidate();
    }

    public final void updateGraphInactiveFill(double d, double d2) {
        this.graphInactiveFillRenderer.activeMinXBound = (float) d;
        this.graphInactiveFillRenderer.activeMaxXBound = (float) d2;
        this.histogram.invalidate();
    }

    interface BinDeterminationDelegate {
        // Tuple2<Integer, BigDecimal> getOptimalBinInformation(List<BigDecimal> list, BigDecimal bigDecimal);
    }

    static class GraphInactiveFillRenderer extends LineChartRenderer {
        float activeMaxXBound;
        float activeMinXBound;
        int inactiveColor;
        private boolean forceInactive;

        GraphInactiveFillRenderer(LineDataProvider lineDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
            super(lineDataProvider, chartAnimator, viewPortHandler);
        }

        /* access modifiers changed from: 0000 */
        public final void setForceInactive(boolean z) {
            this.forceInactive = z;
            if (z) {
                this.activeMinXBound = 0.0f;
                this.activeMaxXBound = 1.0f;
            }
        }

        public final void drawFilledPath(Canvas canvas, Path path, Drawable drawable) {
            ILineDataSet iLineDataSet = this.mChart.getLineData().getDataSetByLabel("com.seatgeek.pricegraphLABEL_DATA", false);
            int save = canvas.save();
            canvas.clipPath(path);
            canvas.drawColor(this.inactiveColor);
            if (!this.forceInactive) {
                Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
                drawable.setBounds((int) transformer.getPixelForValues(this.activeMinXBound, 0.0f).x, (int) this.mViewPortHandler.contentTop(), (int) transformer.getPixelForValues(this.activeMaxXBound, 0.0f).x, (int) this.mViewPortHandler.contentBottom());
                drawable.draw(canvas);
            }
            canvas.restoreToCount(save);
        }
    }

//    public void setListener(Listener listener2) {
//        this.listener = listener2;
//    }
}
