package com.sz.notouchpass.rivalry.interfaces.tune;

public interface Interactor {

    interface OnGetTuneFinishedListener {
        void onRequestSuccess(String response);

        void onRequestError(Throwable throwable);
    }

    void request(String requestString, OnGetTuneFinishedListener listener);

    void dispose();

}
