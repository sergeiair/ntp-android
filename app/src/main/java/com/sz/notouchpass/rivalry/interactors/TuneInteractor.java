package com.sz.notouchpass.rivalry.interactors;

import com.sz.notouchpass.api.GraphQLCall;
import com.sz.notouchpass.rivalry.interfaces.tune.Interactor;
import io.reactivex.disposables.CompositeDisposable;

public class TuneInteractor implements Interactor {

    private CompositeDisposable compositeDisposable;

    public TuneInteractor() {
        compositeDisposable = new CompositeDisposable();
    }

    public void request(String requestString, Interactor.OnGetTuneFinishedListener listener) {
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
