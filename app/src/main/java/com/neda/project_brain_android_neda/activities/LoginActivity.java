package com.neda.project_brain_android_neda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.neda.project_brain_android_neda.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;
    private Button btnRegister;
    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegister = findViewById(R.id.btnRegister);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

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

        if (edtEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(LoginActivity.this, R.string.toast_enter_emailid, Toast.LENGTH_SHORT).show();

        } else if (edtPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(LoginActivity.this, R.string.toast_enter_password, Toast.LENGTH_SHORT).show();

        } else {
            callLoginApi();
        }
    }

    private void callLoginApi() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}