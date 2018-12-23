package com.sz.notouchpass.rivalry.interfaces.activity;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;

public interface RivalryView {
    Resources getResources();

    void setFragment(Fragment fragment);

    void setPredictionFragment(Bundle teamsPredictionBundle);
}

