package com.sz.notouchpass.initial.interfaces;

public interface Presenter {
    void fetchPrediction();

    void clearDisposables();

    void onProgressStart();

    void onProgressDone();

    boolean searchAllowed();

    String getQueryString();
}

