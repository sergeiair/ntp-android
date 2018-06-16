package com.sz.notouchpass.initial.interfaces;

public interface Interactor {
    interface OnGetPredictionFinishedListener {
        void onRequestSuccess(String response);

        void onRequestError(Throwable throwable);
    }

    void request(String requestString, OnGetPredictionFinishedListener listener);

    void dispose();
}
