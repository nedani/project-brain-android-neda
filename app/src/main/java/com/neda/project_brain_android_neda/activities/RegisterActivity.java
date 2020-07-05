package com.neda.project_brain_android_neda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.neda.project_brain_android_neda.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtFirstname;
    private EditText edtLastname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        btnLogIn = findViewById(R.id.btnLogIn);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtFirstname = findViewById(R.id.edtFirstname);
        edtLastname = findViewById(R.id.edtLastname);

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
        if (edtEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, R.string.toast_enter_emailid, Toast.LENGTH_SHORT).show();

        } else if (edtPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, R.string.toast_enter_password, Toast.LENGTH_SHORT).show();

        } else if (edtUsername.getText().toString().trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, R.string.toast_enter_username, Toast.LENGTH_SHORT).show();

        } else if (edtFirstname.getText().toString().trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, R.string.toast_enter_firstname, Toast.LENGTH_SHORT).show();

        } else if (edtLastname.getText().toString().trim().length() == 0) {
            Toast.makeText(RegisterActivity.this, R.string.toast_enter_lastname, Toast.LENGTH_SHORT).show();

        } else {
            callRegisterApi();
        }
    }

    private void callRegisterApi() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        btnLogIn.performClick();
        super.onBackPressed();
    }
}