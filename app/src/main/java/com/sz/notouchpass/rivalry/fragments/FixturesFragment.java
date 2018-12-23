package com.sz.notouchpass.rivalry.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sz.notouchpass.R;
import com.sz.notouchpass.rivalry.models.Fixtures;

public class FixturesFragment extends Fragment {

    public OnListFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fixtures_list, container, false);
            mListener.onFixturesFragmentViewCreated(view);

            return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Fixtures.clearCollection();

        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onFixturesFragmentViewCreated(View view);
    }
}
