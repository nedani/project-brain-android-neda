package com.neda.project_brain_android_neda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.neda.project_brain_android_neda.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.btnRegister:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        btnLogIn.performClick();
        super.onBackPressed();
    }
}