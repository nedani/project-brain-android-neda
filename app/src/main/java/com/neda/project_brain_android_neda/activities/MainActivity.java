package com.neda.project_brain_android_neda.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

public class MainActivity extends AppCompatActivity {

    private SharedPrefsUtil sharedPrefsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefsUtil = new SharedPrefsUtil(MainActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!sharedPrefsUtil.getUsername().equals("")) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 3000);
    }
}