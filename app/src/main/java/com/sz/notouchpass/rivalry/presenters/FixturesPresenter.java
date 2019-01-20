package com.sz.notouchpass.rivalry.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sz.notouchpass.R;
import com.sz.notouchpass.parcelable.TeamsPrediction;
import com.sz.notouchpass.rivalry.fragments.FixturesRecyclerViewAdapter;
import com.sz.notouchpass.rivalry.interactors.FixturesInteractor;
import com.sz.notouchpass.rivalry.interfaces.fixtures.Interactor;
import com.sz.notouchpass.rivalry.interfaces.fixtures.Presenter;
import com.sz.notouchpass.rivalry.models.Fixtures;

public class FixturesPresenter implements
        Presenter,
        Interactor.OnGetFixturesFinishedListener {

    private RecyclerView recyclerView;
    private View fragmentView;
    private FixturesInteractor fixturesInteractor;
    private Resources resources;
    private Bundle extras;
    private RecyclerView list;

    public FixturesPresenter(FixturesInteractor interactor, View view, Bundle extras) {
        this.fragmentView = view;
        this.fixturesInteractor = interactor;
        this.extras = extras;
        this.resources = view.getResources();
        this.list = this.fragmentView.findViewById(R.id.list);

        try {
            initRecyclerView();
        } catch (Exception e) {
            Log.e("InitFixturesView", e.getMessage());
        }
    }

    public void fetchFixtures() {
        fixturesInteractor.request(getQueryString(), this);
    }

    public void clearDisposables() {
        fixturesInteractor.dispose();
    }

    @Override
    public void onRequestError(Throwable throwable) { }

    @Override
    public void onRequestSuccess(String response) {
        Fixtures.clearCollection();

        try {
            JSONArray fixturesArray = new JSONObject(response)
                .getJSONObject("data")
                .getJSONArray("teamsFixtures");

            Fixtures.addItems(fixturesArray);
        } catch (JSONException e) {
            Log.e("FixturesResponse", e.getMessage());
        } finally {
            notifyAdapter();
        }
    }

    public String getQueryString () {
        String queryString = null;

        try {
            TeamsPrediction teamsPredictionData = extras.getParcelable("TeamsPrediction");

            queryString = String.format(
                resources.getString(R.string.teams_fixtures_query),
                teamsPredictionData.getTeam1(),
                teamsPredictionData.getTeam2()
            );
        } catch (Exception e) {
            Log.e("FixturesPresenter", e.getMessage());
        }

        return queryString;
    }

    public void notifyAdapter() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void initRecyclerView() {
        Context context = fragmentView.getContext();

        recyclerView = (RecyclerView) fragmentView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new FixturesRecyclerViewAdapter(Fixtures.itemsList));
    }

}
