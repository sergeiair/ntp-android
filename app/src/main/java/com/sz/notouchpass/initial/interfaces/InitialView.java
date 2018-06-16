package com.sz.notouchpass.initial.interfaces;

import android.content.res.Resources;

public interface InitialView {
    Resources getResources();

    void toMainActivity(String team1, String team2, String rate);
}

