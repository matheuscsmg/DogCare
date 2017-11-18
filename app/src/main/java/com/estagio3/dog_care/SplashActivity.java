package com.estagio3.dog_care;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B9A4")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread timerThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent Login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(Login);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}