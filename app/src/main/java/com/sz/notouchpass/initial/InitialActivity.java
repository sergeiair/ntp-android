package com.sz.notouchpass.initial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sz.notouchpass.MainActivity;
import com.sz.notouchpass.R;
import com.sz.notouchpass.initial.interfaces.InitialView;


public class InitialActivity extends AppCompatActivity implements InitialView {

    private InitialPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        presenter = new InitialPresenter(this, new InitialInteractor());
    }

    @Override
    public void toMainActivity(String team1, String team2, String rate) {
        Intent intent = new Intent(InitialActivity.this, MainActivity.class);
        intent.putExtra("team1", team1);
        intent.putExtra("team2", team2);
        intent.putExtra("rate", rate);

        startActivity(intent);
    }

    public void onDestroy() {
        super.onDestroy();

        presenter.clearDisposables();
    }

}
