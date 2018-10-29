package com.sz.notouchpass.rivalry.presenters;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

import com.sz.notouchpass.R;
import com.sz.notouchpass.helpers.widgets.charts.PieChartBuilder;
import com.sz.notouchpass.parcelable.TeamsPrediction;
import com.sz.notouchpass.rivalry.interactors.PredictionInteractor;
import com.sz.notouchpass.rivalry.interfaces.prediction.Presenter;

public class PredictionPresenter implements Presenter {

    private View fragmentView;
    private PredictionInteractor fixturesInteractor;
    private Resources resources;
    private Bundle extras;

    public PredictionPresenter(PredictionInteractor interactor, View view, Bundle extras) {
        this.fragmentView = view;
        this.fixturesInteractor = interactor;
        this.extras = extras;
        this.resources = view.getResources();

        this.buildCharts(
            extras.getParcelable("TeamsPrediction")
        );
    }

    @Override
    public void buildCharts(TeamsPrediction teamsPrediction) {
        try {
            JSONObject rates = new JSONObject(teamsPrediction.getRates());

            ArrayList<PieEntry> winEntries = new ArrayList<>(Arrays.asList(
                new PieEntry(rates.getInt("homeWin"), teamsPrediction.getTeam1()),
                new PieEntry(rates.getInt("awayWin"), teamsPrediction.getTeam2())
            ));

            ArrayList<PieEntry> drawEntries = new ArrayList<>(Arrays.asList(
                new PieEntry(rates.getInt("homeDraw"), teamsPrediction.getTeam1()),
                new PieEntry(rates.getInt("awayDraw"), teamsPrediction.getTeam2())
            ));

            renderCharts(winEntries, drawEntries);
        }
         catch (JSONException e) {
            Log.e("BuildCharts", e.getMessage());
        }
    }

    @Override
    public void renderCharts(ArrayList<PieEntry> winEntries,
        ArrayList<PieEntry> drawEntries) {

        new PieChartBuilder(fragmentView.findViewById(R.id.pie_chart_win), winEntries)
            .setText("WIN / LOSS", 1.3f, Color.BLACK)
            .decoratePieView(3f, 2f, 40f)
            .decoratePieContent(16f, Color.WHITE)
            .decorateLegend(
                Legend.LegendVerticalAlignment.BOTTOM,
                Legend.LegendHorizontalAlignment.CENTER
            )
            .drawLabels(false)
            .complete();

        new PieChartBuilder(fragmentView.findViewById(R.id.pie_chart_draw), drawEntries)
            .setText("DRAW", 1.6f, Color.BLACK)
            .decoratePieView(3f, 2f, 40f)
            .decoratePieContent(16f, Color.WHITE)
            .decorateLegend(
                Legend.LegendVerticalAlignment.BOTTOM,
                Legend.LegendHorizontalAlignment.CENTER
            )
            .drawLabels(false)
            .complete();
    }

}
