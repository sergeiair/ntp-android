package com.sz.notouchpass.rivalry.interfaces.tune;

import com.sz.notouchpass.rivalry.models.TeamsTuneParams;

public interface Presenter {

    void updateTeamsToggle();

    void updateTeamParams();

    void updateFanSwitch();

    void updateBookmakersSwitch();

    void updateStrikersBar();

    void updateMotivationBar();

    void updateInjureBar();

    void setEvents();

    TeamsTuneParams.TuneParams getTuneParams();
}
