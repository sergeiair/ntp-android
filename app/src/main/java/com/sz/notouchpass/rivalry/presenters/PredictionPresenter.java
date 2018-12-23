package com.sz.notouchpass.rivalry.presenters;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import com.sz.notouchpass.R;
import com.sz.notouchpass.helpers.widgets.charts.PieChartBuilder;
import com.sz.notouchpass.parcelable.TeamsPrediction;
import com.sz.notouchpass.rivalry.interfaces.prediction.Presenter;

public class PredictionPresenter implements Presenter {

    private View fragmentView;
    private Resources resources;

    public PredictionPresenter( View view, Bundle extras) {
        this.fragmentView = view;
        this.resources = view.getResources();

        if (extras.getParcelable("TeamsPrediction") != null) {
            this.buildCharts(
                extras.getParcelable("TeamsPrediction")
            );
        } else {
            this.rebuildCharts(extras);
        }
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
    public void rebuildCharts(Bundle bd) {
        try {
            ArrayList<PieEntry> winEntries = new ArrayList<>(Arrays.asList(
                new PieEntry(bd.getInt("homeWin"), bd.getString("team1")),
                new PieEntry(bd.getInt("awayWin"), bd.getString("team2"))
            ));

            ArrayList<PieEntry> drawEntries = new ArrayList<>(Arrays.asList(
                new PieEntry(bd.getInt("homeDraw"), bd.getString("team1")),
                new PieEntry(bd.getInt("awayDraw"), bd.getString("team2"))
            ));

            renderCharts(winEntries, drawEntries);
        }
        catch (Exception e) {
            Log.e("ReBuildCharts", e.getMessage());
        }
    }

    @Override
    public void renderCharts(ArrayList<PieEntry> winEntries,
        ArrayList<PieEntry> drawEntries) {

            double chartHeight = this.resources.getDisplayMetrics().heightPixels * 0.4;
            PieChart winChartView = fragmentView.findViewById(R.id.pie_chart_win);
            PieChart drawChartView = fragmentView.findViewById(R.id.pie_chart_draw);

            winChartView.setMinimumHeight((int) chartHeight);
            drawChartView.setMinimumHeight((int) chartHeight);

            new PieChartBuilder(winChartView, winEntries, getWinColors())
                .setText("Win / Loss", 1.1f, Color.BLACK)
                .decoratePieView(3f, 2f, 40f)
                .decoratePieContent(16f, Color.WHITE)
                .decorateLegend(
                    Legend.LegendVerticalAlignment.BOTTOM,
                    Legend.LegendHorizontalAlignment.CENTER
                )
                .drawLabels(false)
                .complete();

            new PieChartBuilder(drawChartView, drawEntries, getDrawColors())
                .setText("Draw", 1.4f, Color.BLACK)
                .decoratePieView(3f, 2f, 40f)
                .decoratePieContent(16f, Color.WHITE)
                .decorateLegend(
                    Legend.LegendVerticalAlignment.BOTTOM,
                    Legend.LegendHorizontalAlignment.CENTER
                )
                .drawLabels(false)
                .complete();
    }

    @Override
    public ArrayList<Integer> getWinColors() {
        return new ArrayList<Integer>(
            Arrays.asList(
                Color.rgb(226, 123, 86),
                Color.rgb(92, 70, 66)
            )
        );
    }

    @Override
    public ArrayList<Integer> getDrawColors() {
        return new ArrayList<Integer>(
            Arrays.asList(
                Color.rgb(87, 167, 115),
                Color.rgb(52, 75, 65)
            )
        );
    }

}
