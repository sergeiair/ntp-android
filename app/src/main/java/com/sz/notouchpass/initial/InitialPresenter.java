package com.sz.notouchpass.initial;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.sz.notouchpass.R;
import com.sz.notouchpass.initial.interfaces.InitialView;
import com.sz.notouchpass.initial.interfaces.Interactor;
import com.sz.notouchpass.initial.interfaces.Presenter;
import org.json.JSONException;
import org.json.JSONObject;


public class InitialPresenter implements
        Presenter,
        Interactor.OnGetPredictionFinishedListener,
        View.OnClickListener {

    private InitialView initialView;
    private Interactor initialnteractor;
    private Resources resources;
    private EditText team1;
    private EditText team2;
    private Button predictionBtn;

    public InitialPresenter(InitialView initialView, Interactor initialnteractor) {
        this.initialView = initialView;
        this.initialnteractor = initialnteractor;
        this.resources = initialView.getResources();

        team1 = ((InitialActivity) initialView).findViewById(R.id.inputTeam1);
        team2 = ((InitialActivity) initialView).findViewById(R.id.inputTeam2);
        predictionBtn = ((InitialActivity) initialView).findViewById(R.id.btnGetPrediction);

        predictionBtn.setOnClickListener(this);
    }

    public void getPrediction() {
        String querySting = String.format(
            resources.getString(R.string.teams_prediction_query),
            team1.getText(),
            team2.getText()
        );

        initialnteractor.request(querySting, this);
    }

    public void clearDisposables() {
        initialnteractor.dispose();
    }

    @Override
    public void onRequestError(Throwable throwable) {}

    @Override
    public void onRequestSuccess(String response) {
        try {
            JSONObject responseData = new JSONObject(response).getJSONObject("data");

            String team1Name = team1.getText().toString();
            String team2Name = team2.getText().toString();
            String rate = responseData
                .getJSONObject("teamsPrediction")
                .get("rate")
                .toString();

            initialView.toMainActivity(team1Name, team2Name, rate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override public void onClick(View v) {
        getPrediction();
    }
}
