package com.sz.notouchpass.rivalry.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sz.notouchpass.R;
import com.sz.notouchpass.parcelable.TeamsPrediction;

public class PredictionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public PredictionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_prediction, container, false);

            if (getArguments() != null) {
                mListener.onTunedPredictionFragmentViewCreated(view, getArguments());
            } else {
                mListener.onPredictionFragmentViewCreated(view);
            }

            return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;

        if (getArguments() != null) {
            getArguments().clear();
        }
    }

    public interface OnFragmentInteractionListener {
        void onPredictionFragmentViewCreated(View view);

        void onTunedPredictionFragmentViewCreated(View view, Bundle arguments);
    }
}
