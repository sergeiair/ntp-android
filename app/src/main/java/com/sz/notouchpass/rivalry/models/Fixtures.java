package com.sz.notouchpass.rivalry.models;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Fixtures {

    public static final List<FixtureItem> itemsList = new ArrayList();

    public static void addItems(JSONArray array) {
        try {
            for (int i = 0, len = array.length(); i < len; i++) {
                addItem(new FixtureItem(array.getJSONObject(i)));
            }

        } catch (JSONException e) {
            Log.e("FixturesCollection", e.getMessage());
        }
    }

    public static void clearCollection() {
        itemsList.clear();
    }

    public static class FixtureItem {
        public String stadium;
        public String scheduleDate;
        public String numberGoalTeamHome;
        public String numberGoalTeamAway;
        public String refereeName;
        public String idCountry;

        public FixtureItem(JSONObject data) {
            try {
                stadium = data.getString("stadium");
                scheduleDate = data.getString("schedule_date");
                numberGoalTeamHome = data.getString("number_goal_team_home");
                numberGoalTeamAway = data.getString("number_goal_team_away");
                refereeName = data.getString("referee_name");
                idCountry = data.getString("id_country");
            } catch (JSONException e) {
                stadium = "";
                scheduleDate = "";
                numberGoalTeamHome = "";
                numberGoalTeamAway = "";
                refereeName = "";
                idCountry = "";
            }
        }
    }

    private static void addItem(FixtureItem item) {
        itemsList.add(item);
    }
}
