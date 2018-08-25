package com.pmaptechnotech.pldua.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pmaptechnotech.pldua.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(getApplicationContext(),
                        UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}