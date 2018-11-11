package com.sz.notouchpass.initial;

import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Interactor initialnteractor;
    private Resources resources;
    private EditText team1Input;
    private EditText team2Input;
    private Button predictionBtn;

    public InitialPresenter(InitialView view, Interactor initialnteractor) {
        this.view = view;
        this.initialnteractor = initialnteractor;
        this.resources = view.getResources();

        team1Input = ((InitialActivity) view).findViewById(R.id.inputTeam1);
        team2Input = ((InitialActivity) view).findViewById(R.id.inputTeam2);
        predictionBtn = ((InitialActivity) view).findViewById(R.id.btnGetPrediction);

        predictionBtn.setOnClickListener(this);
    }

    public void fetchPrediction() {
        initialnteractor.request(getQueryString(), this);
    }

    public void clearDisposables() {
        initialnteractor.dispose();
    }

    @Override
    public void onRequestError(Throwable throwable) {
        predictionBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(String response) {
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
            e.printStackTrace();
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
