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
        ArrayList<PieEntry> drawEntries
    );

    ArrayList<Integer> getWinColors();

    ArrayList<Integer> getDrawColors();
}
