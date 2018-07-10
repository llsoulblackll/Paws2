package com.app.pawapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.app.pawapp.Login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        progressBar = findViewById(R.id.ProgressBar);

        new Thread(new Runnable() {
            public void run() {
                loading();
                startLogin();
                finish();
            }
        }).start();

    }

    private void loading() {
        for (int progress=0; progress<100; progress+=10) {
            try {
                Thread.sleep(250);
                progressBar.setProgress(progress);
            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e.getCause());
            }
        }
    }

    private void startLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
    }

}