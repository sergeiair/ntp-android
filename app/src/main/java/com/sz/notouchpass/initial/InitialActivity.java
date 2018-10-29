package com.sz.notouchpass.initial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import com.sz.notouchpass.R;
import com.sz.notouchpass.initial.interfaces.InitialView;
import com.sz.notouchpass.rivalry.RivalryActivity;

public class InitialActivity extends AppCompatActivity implements InitialView {

    private InitialPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        presenter = new InitialPresenter(this, new InitialInteractor());
    }

    @Override
    public void toRivalryActivity(Parcelable teamsPrediction) {
        Intent intent = new Intent(InitialActivity.this, RivalryActivity.class);
        intent.putExtra("TeamsPrediction", teamsPrediction);

        startActivity(intent);
    }

    public void onDestroy() {
        super.onDestroy();

        presenter.clearDisposables();
    }

}
