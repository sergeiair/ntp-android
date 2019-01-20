package com.sz.notouchpass.rivalry.models;

public class TeamsTuneParams {

    public static TuneParams team1 = new TuneParams();

    public static TuneParams team2 = new TuneParams();

    public static class TuneParams {
        public boolean bookmakersOnWin = false;

        public boolean newCoach = false;

        public int keyPlayersInjure = 0;

        public int teamMotivation = 5;

        public int keyStrikersForm = 5;
    }

    public static String toRequestBodyString() {
        return "team1newCoach:" + team1.newCoach +
               ",team1bookmakersOnWin:" + team1.bookmakersOnWin +
               ",team1keyPlayersInjure:" + team1.keyPlayersInjure +
               ",team1teamMotivation:" + team1.teamMotivation +
               ",team1keyStrikersForm:" + team1.keyStrikersForm +
               ",team2newCoach:" + team2.newCoach +
               ",team2bookmakersOnWin:" + team2.bookmakersOnWin +
               ",team2keyPlayersInjure:" + team2.keyPlayersInjure +
               ",team2teamMotivation:" + team2.teamMotivation +
               ",team2keyStrikersForm:" + team2.keyStrikersForm;
    }
}
