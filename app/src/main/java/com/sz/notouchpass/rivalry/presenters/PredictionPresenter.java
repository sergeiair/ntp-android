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

            int drawChance = getCalculatedDrawChance(
                rates.getInt("homeDraw"),
                rates.getInt("awayDraw")
            );
            ArrayList<PieEntry> drawEntries = new ArrayList<>(Arrays.asList(
                new PieEntry(drawChance, "Draw"),
                new PieEntry(100 - drawChance, "Another result")
            ));

            renderCharts(winEntries, drawEntries, false);
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

            int drawChance = getCalculatedDrawChance(
                bd.getInt("homeDraw"),
                bd.getInt("awayDraw")
            );
            ArrayList<PieEntry> drawEntries = new ArrayList<>(Arrays.asList(
                new PieEntry(drawChance, "Draw"),
                new PieEntry(100 - drawChance, "Another result")
            ));

            renderCharts(winEntries, drawEntries, true);
        }
        catch (Exception e) {
            Log.e("ReBuildCharts", e.getMessage());
        }
    }

    @Override
    public int getCalculatedDrawChance(int team1Rate, int team2Rate) {
        Double chance = (50 - Math.abs(team1Rate - team2Rate)) * 1.2;
        return chance.intValue();
    }

    @Override
    public void renderCharts(ArrayList<PieEntry> winEntries,
        ArrayList<PieEntry> drawEntries,
        boolean isCustomized) {

            double chartHeight = this.resources.getDisplayMetrics().heightPixels * 0.4;
            PieChart winChartView = fragmentView.findViewById(R.id.pie_chart_win);
            PieChart drawChartView = fragmentView.findViewById(R.id.pie_chart_draw);

            winChartView.setMinimumHeight((int) chartHeight);
            drawChartView.setMinimumHeight((int) chartHeight);

            new PieChartBuilder(winChartView, winEntries, getWinColors(isCustomized))
                .setText("Chance to win", 1f, Color.BLACK)
                .decoratePieView(3f, 2f, 45f)
                .decoratePieContent(14f, Color.WHITE)
                .decorateLegend(
                    Legend.LegendVerticalAlignment.BOTTOM,
                    Legend.LegendHorizontalAlignment.CENTER
                )
                .drawLabels(false)
                .complete();

            new PieChartBuilder(drawChartView, drawEntries, getDrawColors(isCustomized))
                .setText("Chance of draw", 1f, Color.BLACK)
                .decoratePieView(3f, 2f, 50f)
                .decoratePieContent(13f, Color.WHITE)
                .decorateLegend(
                    Legend.LegendVerticalAlignment.BOTTOM,
                    Legend.LegendHorizontalAlignment.CENTER
                )
                .drawLabels(false)
                .complete();
    }

    @Override
    public ArrayList<Integer> getWinColors(boolean isCustomized) {
        return new ArrayList<Integer>(
            Arrays.asList(
                Color.rgb(80, 81, 79),
                !isCustomized
                    ? Color.rgb(242, 95, 92)
                    : Color.rgb(3, 160, 176)
            )
        );
    }

    @Override
    public ArrayList<Integer> getDrawColors(boolean isCustomized) {
        return new ArrayList<Integer>(
            Arrays.asList(
                !isCustomized
                    ? Color.rgb(126,167,49)
                    : Color.rgb(3, 160, 176),
                Color.rgb(220,220,220)
            )
        );
    }

}
