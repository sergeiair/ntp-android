package com.sz.notouchpass.rivalry.interactors;

import io.reactivex.disposables.CompositeDisposable;

import com.sz.notouchpass.api.GraphQLCall;
import com.sz.notouchpass.rivalry.interfaces.fixtures.Interactor;

public class FixturesInteractor implements Interactor {

    private CompositeDisposable compositeDisposable;

    public FixturesInteractor() {
        compositeDisposable = new CompositeDisposable();
    }

    public void request(String requestString, OnGetFixturesFinishedListener listener) {
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
