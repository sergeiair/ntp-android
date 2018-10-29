package com.sz.notouchpass.rivalry.interfaces.prediction;

import com.github.mikephil.charting.data.PieEntry;
import com.sz.notouchpass.parcelable.TeamsPrediction;

import java.util.ArrayList;

public interface Presenter {

    void buildCharts(TeamsPrediction teamsPrediction);

    void renderCharts(
        ArrayList<PieEntry> winEntries,
        ArrayList<PieEntry> drawEntries
    );
}
