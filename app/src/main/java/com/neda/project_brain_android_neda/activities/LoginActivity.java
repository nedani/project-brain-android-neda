package com.neda.project_brain_android_neda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.neda.project_brain_android_neda.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                checkValidation();
                break;

            case R.id.btnRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }

    private void checkValidation() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}