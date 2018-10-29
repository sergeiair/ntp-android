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

import java.text.DateFormat;
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
            String stadiumImageName = stadiumName.replace(" ", "").toLowerCase();
            String imagePath = "android.resource://com.sz.notouchpass/drawable/";
            String score = mValues.get(position).numberGoalTeamHome + ":" + mValues.get(position).numberGoalTeamAway;

            holder.mStadImageView.setImageURI(Uri.parse(imagePath + stadiumImageName));
            holder.mDateView.setText(DateFormat.getDateInstance().format(parsedDate));
            holder.mStadNameView.setText(stadiumName);
            holder.mScoreView.setText(score);
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
        public final TextView mDateView;
        public final TextView mStadNameView;
        public final TextView mScoreView;
        public final ImageView mStadImageView;
        public FixtureItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = view.findViewById(R.id.date);
            mStadNameView = view.findViewById(R.id.stadName);
            mStadImageView = view.findViewById(R.id.stadImage);
            mScoreView = view.findViewById(R.id.score);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'";
        }
    }
}
