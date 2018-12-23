package com.sz.notouchpass.initial;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
        View.OnClickListener {

    private InitialView view;
    private Interactor initialInteractor;
    private Resources resources;
    private Context ctx;
    private EditText team1Input;
    private EditText team2Input;
    private Button predictionBtn;
    private ProgressBar progressBar;

    public InitialPresenter(InitialView view, Interactor initialInteractor) {
        this.view = view;
        this.initialInteractor = initialInteractor;
        this.resources = view.getResources();
        this.ctx = ((InitialActivity) view).getApplicationContext();

        team1Input = ((InitialActivity) view).findViewById(R.id.inputTeam1);
        team2Input = ((InitialActivity) view).findViewById(R.id.inputTeam2);
        predictionBtn = ((InitialActivity) view).findViewById(R.id.btnGetPrediction);
        progressBar = ((InitialActivity) view).findViewById(R.id.progressBar);

        predictionBtn.setOnClickListener(this);
    }

    public void fetchPrediction() {
        predictionBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        initialInteractor.request(getQueryString(), this);
    }

    public void clearDisposables() {
        initialInteractor.dispose();
    }

    @Override
    public void onRequestError(Throwable throwable) {
        predictionBtn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRequestSuccess(String response) {
        progressBar.setVisibility(View.GONE);
        predictionBtn.setVisibility(View.VISIBLE);

        try {
            JSONObject responseData = new JSONObject(response).getJSONObject("data");
            String team1Name = team1Input.getText().toString();
            String team2Name = team2Input.getText().toString();
            String rates = responseData
                .getJSONObject("teamsPrediction")
                .get("rates")
                .toString();

            view.toRivalryActivity(new TeamsPrediction(team1Name, team2Name, rates));
        } catch (JSONException e) {
            Toast.makeText(ctx, "Failed to find history", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        fetchPrediction();
    }

    public String getQueryString () {
        return String.format(
            resources.getString(R.string.teams_prediction_query),
            team1Input.getText(),
            team2Input.getText()
        );
    }
}
