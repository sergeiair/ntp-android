package com.sz.notouchpass.rivalry.interfaces.fixtures;

public interface Interactor {
    interface OnGetFixturesFinishedListener {
        void onRequestSuccess(String response);

        void onRequestError(Throwable throwable);
    }

    void request(String requestString, Interactor.OnGetFixturesFinishedListener listener);

    void dispose();
}
