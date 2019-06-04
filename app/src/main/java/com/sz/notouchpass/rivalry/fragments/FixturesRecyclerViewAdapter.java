package com.sz.notouchpass.rivalry.fragments;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sz.notouchpass.R;
import com.sz.notouchpass.rivalry.models.Fixtures.FixtureItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FixturesRecyclerViewAdapter extends RecyclerView.Adapter<FixturesRecyclerViewAdapter.ViewHolder> {

    private final List<FixtureItem> mValues;
    private final SimpleDateFormat sdf;

    public FixturesRecyclerViewAdapter(List<FixtureItem> items) {
        mValues = items;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_fixture, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.mItem = mValues.get(position);

            Date parsedDate = sdf.parse(mValues.get(position).scheduleDate);
            String stadiumName = mValues.get(position).stadium;
            String stadiumImageName = stadiumName.replaceAll("[^0-9a-zA-Z]", "").toLowerCase();
            String imagePath = "android.resource://com.sz.notouchpass/raw/";
            String formattedDate = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH)
                .format(parsedDate)
                .replace("-", "\n\r");
            String team1 = mValues.get(position).teamSeasonHomeName + " " +
                mValues.get(position).numberGoalTeamHome;
            String team2 = mValues.get(position).numberGoalTeamAway + " " +
                mValues.get(position).teamSeasonAwayName;

            holder.mStadImageView.setImageURI(Uri.parse(imagePath + stadiumImageName));
            holder.mDateView.setText(formattedDate);
            holder.mStadNameView.setText(!stadiumName.equals("unknown") ? stadiumName : "Unknown stadium");
            holder.mTeamsView.setText(team1 + " : " + team2);
        } catch (Exception e) {
            Log.e("SetFixtureContent", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTeamsView;
        public final TextView mDateView;
        public final TextView mStadNameView;
        public final ImageView mStadImageView;
        public FixtureItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = view.findViewById(R.id.date);
            mStadNameView = view.findViewById(R.id.stadName);
            mStadImageView = view.findViewById(R.id.stadImage);
            mTeamsView = view.findViewById(R.id.teams);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'";
        }
    }
}
