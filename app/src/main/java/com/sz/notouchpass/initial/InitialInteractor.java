package com.sz.notouchpass.initial;

import com.sz.notouchpass.api.GraphQLCall;
import com.sz.notouchpass.initial.interfaces.Interactor;

import io.reactivex.disposables.CompositeDisposable;

public class InitialInteractor implements Interactor {

    private CompositeDisposable compositeDisposable;

    public InitialInteractor() {
        compositeDisposable = new CompositeDisposable();
    }

    public void request(String requestString, OnGetPredictionFinishedListener listener) {
        compositeDisposable.add(
            GraphQLCall
                .post(requestString)
                .subscribe(
                    listener::onRequestSuccess,
                    listener::onRequestError
                )
        );
    }

    public void dispose() {
        compositeDisposable.clear();
    }
}
