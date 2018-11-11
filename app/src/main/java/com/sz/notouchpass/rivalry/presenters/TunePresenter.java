package com.sz.notouchpass.rivalry.presenters;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.sz.notouchpass.R;
import com.sz.notouchpass.parcelable.TeamsPrediction;
import com.sz.notouchpass.rivalry.interfaces.tune.Presenter;
import com.sz.notouchpass.rivalry.models.TeamsTuneParams;

public class TunePresenter implements Presenter {

    private View fragmentView;
    private Resources resources;
    private Bundle extras;
    private Switch bookmakersSwitch;
    private Switch fanSwitch;
    private ToggleButton teamToggle;
    private SeekBar injureSeekBar;
    private SeekBar motivationSeekBar;
    private SeekBar strikersSeekBar;
    private Button requestBtn;

    public TunePresenter(View view, Bundle extras) {
        this.extras = extras;

        fragmentView = view;
        resources = view.getResources();
        teamToggle = this.fragmentView.findViewById(R.id.teamToggle);
        bookmakersSwitch = this.fragmentView.findViewById(R.id.bookmakersWin);
        fanSwitch = this.fragmentView.findViewById(R.id.meIsFan);
        injureSeekBar = this.fragmentView.findViewById(R.id.injureBar);
        motivationSeekBar = this.fragmentView.findViewById(R.id.motivationBar);
        strikersSeekBar = this.fragmentView.findViewById(R.id.strikersBar);
        requestBtn = this.fragmentView.findViewById(R.id.requestBtn);

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
        fanSwitch.setChecked(
            getTuneParams().isFan
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
            TeamsPrediction teamsPredictionData = extras.getParcelable("TeamsPrediction");

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

    public void setEvents() {
        requestBtn.setOnClickListener((View v) -> {

        });

        teamToggle.setOnCheckedChangeListener((CompoundButton bv, boolean isChecked) -> {
            updateTeamParams();
        });

        bookmakersSwitch.setOnCheckedChangeListener((CompoundButton bv, boolean isChecked) -> {
            getTuneParams().bookmakersOnWin = isChecked;
        });

        fanSwitch.setOnCheckedChangeListener((CompoundButton bv, boolean isChecked) -> {
            getTuneParams().isFan = isChecked;
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
