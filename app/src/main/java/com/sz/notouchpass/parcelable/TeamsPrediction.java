package com.sz.notouchpass.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class TeamsPrediction implements Parcelable {
    private String team1;
    private String team2;
    private String rates;

    public TeamsPrediction(String team1, String team2, String rates) {
        this.team1 = team1;
        this.team2 = team2;
        this.rates = rates;
    }

    private TeamsPrediction(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);

        team1 = data[0];
        team2 = data[1];
        rates = data[2];
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getRates() {
        return rates;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeStringArray(new String[] { team1, team2, rates });
    }

    public static final Parcelable.Creator<TeamsPrediction> CREATOR
            = new Parcelable.Creator<TeamsPrediction>() {
        public TeamsPrediction createFromParcel(Parcel in) {
            return new TeamsPrediction(in);
        }

        public TeamsPrediction[] newArray(int size) {
            return new TeamsPrediction[size];
        }
    };

}
