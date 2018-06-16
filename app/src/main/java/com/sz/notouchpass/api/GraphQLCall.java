package com.sz.notouchpass.api;

import java.io.IOException;
import java.util.concurrent.Callable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GraphQLCall {

    private static final MediaType JSON =
        MediaType.parse("application/json; charset=utf-8");

    private static final Request.Builder requestBuilder =
        new Request.Builder().url("http://138.68.248.240:8080/graphql");

    private static final OkHttpClient client = new OkHttpClient();

    public static Observable<String> post(String query) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Response response = client
                    .newCall(getRequest(query))
                    .execute();

                try {
                    return response.body().string();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    private static Request getRequest(String query) {
        RequestBody body = RequestBody.create(JSON, query);

        return requestBuilder
            .post(body)
            .build();
    }
}
