package com.sz.notouchpass.rivalry.interfaces.fixtures;

public interface Presenter {

    void fetchFixtures();

    void clearDisposables();

    void notifyAdapter();

    void initRecyclerView();

    String getQueryString();

}
