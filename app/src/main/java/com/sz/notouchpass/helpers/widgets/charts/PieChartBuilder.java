package com.sz.notouchpass.helpers.widgets.charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartBuilder {

    private final PieChart mChart;;
    private final PieDataSet dataSet;

    public PieChartBuilder
        (PieChart chartView, ArrayList<PieEntry> entries, ArrayList<Integer> colors) {
            mChart = chartView;

            mChart.setUsePercentValues(true);
            mChart.getDescription().setEnabled(false);

            dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);
    }

    public PieChartBuilder setText(String text, float size, int color) {
        mChart.setCenterText(getSpannableText(text, size, color));

        return this;
    }

    public PieChartBuilder drawLabels(boolean draw) {
        mChart.setDrawEntryLabels(draw);

        return this;
    }

    public PieChartBuilder decoratePieView
            (float sliceSpace, float selectionShift, float holeRadius) {
        dataSet.setSliceSpace(sliceSpace);
        dataSet.setSelectionShift(selectionShift);

        mChart.setHoleRadius(holeRadius);
        mChart.setTransparentCircleAlpha(0);

        return this;
    }

    public PieChartBuilder decoratePieContent(float valueTextSize, int color) {
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(valueTextSize);
        data.setValueTextColor(color);

        mChart.setData(data);

        return this;
    }

    public PieChartBuilder decorateLegend(
            Legend.LegendVerticalAlignment vAlignment,
            Legend.LegendHorizontalAlignment hAlignment) {

        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(vAlignment);
        legend.setHorizontalAlignment(hAlignment);
        legend.setDrawInside(false);

        return this;
    }

    public void complete() {
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    private SpannableString getSpannableText(String text, float size, int color) {
        SpannableString s = new SpannableString(text);

        s.setSpan(new RelativeSizeSpan(size), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(color), 0, s.length(), 0);

        return s;
    }


}

