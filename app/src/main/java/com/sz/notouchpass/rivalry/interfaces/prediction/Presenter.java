package com.sz.notouchpass.rivalry.interfaces.prediction;

import android.os.Bundle;

import com.github.mikephil.charting.data.PieEntry;
import com.sz.notouchpass.parcelable.TeamsPrediction;

import java.util.ArrayList;

public interface Presenter {

    void buildCharts(TeamsPrediction teamsPrediction);

    void rebuildCharts(Bundle teamsPredictionBundle);

    void renderCharts(
        ArrayList<PieEntry> winEntries,
        ArrayList<PieEntry> drawEntries,
        boolean isCustomized
    );

    int getCalculatedDrawChance(int team1DrawRate, int team2DrawRate);

    ArrayList<Integer> getWinColors(boolean isCustomized);

    ArrayList<Integer> getDrawColors(boolean isCustomized);
}
