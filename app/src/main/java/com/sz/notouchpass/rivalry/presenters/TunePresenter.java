package com.sz.notouchpass.rivalry.presenters;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.sz.notouchpass.R;
import com.sz.notouchpass.initial.InitialActivity;
import com.sz.notouchpass.parcelable.TeamsPrediction;
import com.sz.notouchpass.rivalry.RivalryActivity;
import com.sz.notouchpass.rivalry.interactors.TuneInteractor;
import com.sz.notouchpass.rivalry.interfaces.activity.RivalryView;
import com.sz.notouchpass.rivalry.interfaces.tune.Interactor;
import com.sz.notouchpass.rivalry.interfaces.tune.Presenter;
import com.sz.notouchpass.rivalry.models.TeamsTuneParams;

import org.json.JSONException;
import org.json.JSONObject;

public class TunePresenter implements Presenter, Interactor.OnGetTuneFinishedListener {

    private View fragmentView;
    private Resources resources;
    private Bundle extras;
    private Switch bookmakersSwitch;
    private Switch coachSwitch;
    private ToggleButton teamToggle;
    private SeekBar injureSeekBar;
    private SeekBar motivationSeekBar;
    private SeekBar strikersSeekBar;
    private Button requestBtn;
    private TeamsPrediction teamsPredictionData;
    private TuneInteractor tuneInteractor;
    private ProgressBar progressBar;

    public TunePresenter(View view, Bundle extras, Interactor interactor) {
        this.extras = extras;

        tuneInteractor = (TuneInteractor) interactor;
        teamsPredictionData = extras.getParcelable("TeamsPrediction");
        fragmentView = view;
        resources = view.getResources();
        teamToggle = this.fragmentView.findViewById(R.id.teamToggle);
        bookmakersSwitch = this.fragmentView.findViewById(R.id.bookmakersWin);
        coachSwitch = this.fragmentView.findViewById(R.id.newCoach);
        injureSeekBar = this.fragmentView.findViewById(R.id.injureBar);
        motivationSeekBar = this.fragmentView.findViewById(R.id.motivationBar);
        strikersSeekBar = this.fragmentView.findViewById(R.id.strikersBar);
        requestBtn = this.fragmentView.findViewById(R.id.requestBtn);
        progressBar = this.fragmentView.findViewById(R.id.progressBar);

        updateTeamsToggle();
        updateTeamParams();
        setEvents();
    }

    public void updateTeamParams() {
        updateBookmakersSwitch();
        updateFanSwitch();
        updateInjureBar();
        updateMotivationBar();
        updateStrikersBar();
    }

    public void updateBookmakersSwitch() {
        bookmakersSwitch.setChecked(
            getTuneParams().bookmakersOnWin
        );
    }

    public void updateFanSwitch() {
        coachSwitch.setChecked(
            getTuneParams().newCoach
        );
    }

    public void updateInjureBar() {
        injureSeekBar.setProgress(
            getTuneParams().keyPlayersInjure
        );
    }

    public void updateMotivationBar() {
        motivationSeekBar.setProgress(
            getTuneParams().teamMotivation
        );
    }

    public void updateStrikersBar() {
        strikersSeekBar.setProgress(
            getTuneParams().keyStrikersForm
        );
    }

    public void updateTeamsToggle() {
        try {
            teamToggle.setTextOn(teamsPredictionData.getTeam1());
            teamToggle.setTextOff(teamsPredictionData.getTeam2());
            teamToggle.setChecked(true);
        } catch (Exception e) {
            Log.e("TunePresenter", e.getMessage());
        }
    }

    public TeamsTuneParams.TuneParams getTuneParams() {
        return teamToggle.isChecked()
            ? TeamsTuneParams.team1
            : TeamsTuneParams.team2;
    }

    public String getQueryString () {
        return String.format(
            resources.getString(R.string.teams_prediction_tune_query),
            TeamsTuneParams.toRequestBodyString() + getRatesQueryPart()
        );
    }

    public String getRatesQueryPart () {
        String queryPart = "";

        try {
            JSONObject rates = new JSONObject(teamsPredictionData.getRates());

            queryPart += ",team1winRate:" + rates.getString("homeWin") +
                         ",team1drawRate:" + rates.getString("homeDraw") +
                         ",team2winRate:" + rates.getString("awayWin") +
                         ",team2drawRate:" + rates.getString("awayDraw");
        } catch (JSONException e) {
            Log.e("TunePresenter", e.getMessage());
        }

        return queryPart;
    }

    @Override
    public void onRequestError(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        requestBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(String response) {
        progressBar.setVisibility(View.GONE);
        requestBtn.setVisibility(View.VISIBLE);

        try {
            Bundle predictionBundle = new Bundle();
            JSONObject responseData = new JSONObject(response).getJSONObject("data");
            JSONObject rates = responseData
                .getJSONObject("predictionTune")
                .getJSONObject("rates");

            predictionBundle.putString("team1", teamsPredictionData.getTeam1());
            predictionBundle.putString("team2", teamsPredictionData.getTeam2());
            predictionBundle.putInt("homeWin", rates.getInt("homeWin"));
            predictionBundle.putInt("homeDraw", rates.getInt("homeDraw"));
            predictionBundle.putInt("awayWin", rates.getInt("awayWin"));
            predictionBundle.putInt("awayDraw", rates.getInt("awayDraw"));

            ((RivalryView) fragmentView
                .getContext())
                .setPredictionFragment(predictionBundle);
        } catch (JSONException e) {
            Log.e("InitialPresenter", e.getMessage());
        }
    }

    public void setEvents() {
        requestBtn.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            requestBtn.setVisibility(View.GONE);

            tuneInteractor.request(getQueryString(), this);
        });

        teamToggle.setOnCheckedChangeListener((CompoundButton bv, boolean isChecked) -> {
            updateTeamParams();
        });

        bookmakersSwitch.setOnCheckedChangeListener((CompoundButton bv, boolean isChecked) -> {
            getTuneParams().bookmakersOnWin = isChecked;
        });

        coachSwitch.setOnCheckedChangeListener((CompoundButton bv, boolean isChecked) -> {
            getTuneParams().newCoach = isChecked;
        });

        injureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getTuneParams().keyPlayersInjure = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        motivationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getTuneParams().teamMotivation = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        strikersSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getTuneParams().keyStrikersForm = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
