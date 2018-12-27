package com.sz.notouchpass.initial;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.sz.notouchpass.R;
import com.sz.notouchpass.initial.interfaces.InitialView;
import com.sz.notouchpass.initial.interfaces.Interactor;
import com.sz.notouchpass.initial.interfaces.Presenter;
import com.sz.notouchpass.parcelable.TeamsPrediction;
import org.json.JSONException;
import org.json.JSONObject;

public class InitialPresenter implements
        Presenter,
        Interactor.OnGetPredictionFinishedListener,
        Spinner.OnItemSelectedListener,
        View.OnClickListener {

    private InitialView view;
    private Interactor initialInteractor;
    private Resources resources;
    private Context ctx;
    private Spinner team1Spinner;
    private Spinner team2Spinner;
    private String team1;
    private String team2;
    private Button predictionBtn;
    private ProgressBar progressBar;

    public InitialPresenter(InitialView view, Interactor initialInteractor) {
        this.view = view;
        this.initialInteractor = initialInteractor;
        this.resources = view.getResources();
        this.ctx = ((InitialActivity) view).getApplicationContext();

        team1Spinner = ((InitialActivity) view).findViewById(R.id.inputTeam1);
        team2Spinner = ((InitialActivity) view).findViewById(R.id.inputTeam2);
        predictionBtn = ((InitialActivity) view).findViewById(R.id.btnGetPrediction);
        progressBar = ((InitialActivity) view).findViewById(R.id.progressBar);

        predictionBtn.setOnClickListener(this);
        team1Spinner.setOnItemSelectedListener(this);
        team2Spinner.setOnItemSelectedListener(this);

        team1Spinner.setSelection(1);
        team2Spinner.setSelection(2);
    }

    public void fetchPrediction() {
        if (searchAllowed()) {
            initialInteractor.request(
                    getQueryString(), this
            );

            onProgressStart();
        } else {
            Toast.makeText(ctx, "Please check selected teams", Toast.LENGTH_LONG).show();
        }
    }

    public void clearDisposables() {
        initialInteractor.dispose();
    }

    public void onProgressStart() {
        predictionBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onProgressDone() {
        progressBar.setVisibility(View.GONE);
        predictionBtn.setVisibility(View.VISIBLE);
    }

    public boolean searchAllowed() {
        return team1 != null && team2 != null && !team1.equals(team2);
    }

    public void onRequestError(Throwable throwable) {
        onProgressDone();
    }

    public void onRequestSuccess(String response) {
        onProgressDone();

        try {
            JSONObject responseData = new JSONObject(response).getJSONObject("data");
            String rates = responseData
                .getJSONObject("teamsPrediction")
                .get("rates")
                .toString();

            view.toRivalryActivity(new TeamsPrediction(team1, team2, rates));
        } catch (JSONException e) {
            Toast.makeText(ctx, "Failed to find history", Toast.LENGTH_LONG).show();
        }
    }


    public void onClick(View v) {
        fetchPrediction();
    }

    public void onItemSelected(AdapterView<?> av, View var2, int var3, long var4) {
        if (av.getTag().equals("team1")) {
            team1 = av.getSelectedItem().toString();
        } else {
            team2 = av.getSelectedItem().toString();
        }
    }

    public void onNothingSelected(AdapterView<?> var1) {};

    public String getQueryString () {
        return String.format(
            resources.getString(R.string.teams_prediction_query),
            team1,
            team2
        );
    }
}
