package com.sz.notouchpass.rivalry.presenters;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.sz.notouchpass.rivalry.interfaces.tune.Presenter;

public class TunePresenter implements Presenter {

    private View fragmentView;
    private Resources resources;
    private Bundle extras;

    public TunePresenter(View view, Bundle extras) {
        this.fragmentView = view;
        this.extras = extras;
        this.resources = view.getResources();
    }
}
