package com.sz.notouchpass;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sz.notouchpass.dummy.DummyContent;

public class MainActivity extends FragmentActivity implements FixtureFragment.OnListFragmentInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
         = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        // Do different stuff
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();

        //TextView tvPredictionRate = (TextView) findViewById(R.id.predictionRate);

        //tvPredictionRate.setText(intent.getStringExtra("rate"));

        FragmentManager fm = getFragmentManager();

        Fragment selectedFragment = null;
        selectedFragment = FixtureFragment.newInstance(5);


            // During initial setup, plug in the details fragment.
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.listcontainer, selectedFragment);
// alternatively add it with a tag
// trx.add(R.id.your_placehodler, new YourFragment(), "detail");
            ft.commit();

    }

}
