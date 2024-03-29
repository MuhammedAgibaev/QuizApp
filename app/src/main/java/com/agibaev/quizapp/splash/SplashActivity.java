package com.agibaev.quizapp.splash;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;

import com.agibaev.quizapp.R;
import com.agibaev.quizapp.main.MainActivity;


public class SplashActivity extends AppCompatActivity {
    private long mills = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSplashTime();
    }

    private void setSplashTime() {
        new CountDownTimer(mills, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mills = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                getMainActivity();
            }
        }.start();
    }

    private void getMainActivity() {
        MainActivity.start(this);
        finish();
    }
}
