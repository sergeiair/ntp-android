package com.sz.notouchpass.rivalry;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import com.sz.notouchpass.R;
import com.sz.notouchpass.rivalry.fragments.FixturesFragment;
import com.sz.notouchpass.rivalry.fragments.InfoFragment;
import com.sz.notouchpass.rivalry.fragments.PredictionFragment;
import com.sz.notouchpass.rivalry.fragments.TuneFragment;
import com.sz.notouchpass.rivalry.interactors.FixturesInteractor;
import com.sz.notouchpass.rivalry.interactors.TuneInteractor;
import com.sz.notouchpass.rivalry.interfaces.activity.RivalryView;
import com.sz.notouchpass.rivalry.presenters.FixturesPresenter;
import com.sz.notouchpass.rivalry.presenters.PredictionPresenter;
import com.sz.notouchpass.rivalry.presenters.TunePresenter;

public class RivalryActivity extends FragmentActivity implements
        RivalryView,
        TuneFragment.OnFragmentInteractionListener,
        PredictionFragment.OnFragmentInteractionListener,
        FixturesFragment.OnListFragmentInteractionListener {

    private FragmentManager fm;
    private TunePresenter tunePresenter;
    private PredictionPresenter predictionPresenter;
    private FixturesPresenter fixturesPresenter;
    private Bundle extrasBd;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_prediction:
                    setFragment(new PredictionFragment());

                    return true;
                case R.id.navigation_fixtures:
                    setFragment(new FixturesFragment());

                    return true;
                case R.id.navigation_customize:
                    setFragment(new TuneFragment());

                    return true;

                case R.id.navigation_info:
                    setFragment(new InfoFragment());

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rivalry);

        Intent intent = getIntent();
        BottomNavigationView navigation = findViewById(R.id.navigation);

        extrasBd = intent.getExtras();
        fm = getFragmentManager();

        setFragment(new PredictionFragment());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_prediction);
    }


    @Override
    public void onPredictionFragmentViewCreated(View view) {
        predictionPresenter = new PredictionPresenter (
            view,
            extrasBd
        );
    }

    @Override
    public void onTunedPredictionFragmentViewCreated(View view, Bundle arguments) {
        predictionPresenter = new PredictionPresenter (
            view,
            arguments
        );
    }

    @Override
    public void onFixturesFragmentViewCreated(View view) {
        fixturesPresenter = new FixturesPresenter (
            new FixturesInteractor(),
            view,
            extrasBd
        );

        fixturesPresenter.fetchFixtures();
    }

    @Override
    public void onTuneFragmentViewCreated(View view) {
        tunePresenter = new TunePresenter (
            view,
            extrasBd,
            new TuneInteractor()
        );
    }

    public void setFragment(Fragment fragment) {
        fm.beginTransaction()
            .replace(R.id.fragmentContent, fragment)
            .commit();
    }

    public void setPredictionFragment(Bundle teamsPredictionBundle) {
        Fragment predictionFragment = new PredictionFragment();

        predictionFragment.setArguments(teamsPredictionBundle);
        setFragment(predictionFragment);
    }
}
