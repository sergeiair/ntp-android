package com.sz.notouchpass.rivalry.models;

public class TeamsTuneParams {

    public static TuneParams team1 = new TuneParams();

    public static TuneParams team2 = new TuneParams();

    public static class TuneParams {
        public boolean bookmakersOnWin = true;

        public boolean isFan = false;

        public int keyPlayersInjure = 0;

        public int teamMotivation = 5;

        public int keyStrikersForm = 5;
    }
}
