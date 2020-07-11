package com.neda.project_brain_android_neda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.callback.ApiCallBackPost;
import com.neda.project_brain_android_neda.form.RegisterForm;
import com.neda.project_brain_android_neda.model.RegisterResponseModel;
import com.neda.project_brain_android_neda.rest.PostTaskJson;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ApiCallBackPost<RegisterResponseModel> {

    private Button btnLogin;
    private Button btnRegister;
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
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtFirstname = findViewById(R.id.edtFirstname);
        edtLastname = findViewById(R.id.edtLastname);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
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
        RegisterForm registerForm = new RegisterForm();
        registerForm.setEmail(edtEmail.getText().toString());
        registerForm.setPassword(edtPassword.getText().toString());
        registerForm.setFirstname(edtFirstname.getText().toString());
        registerForm.setLastname(edtLastname.getText().toString());
        registerForm.setUsername(edtUsername.getText().toString());
        new PostTaskJson<RegisterForm, RegisterResponseModel>(RegisterResponseModel.class, this).execute(registerForm);
    }

    @Override
    public void onBackPressed() {
        btnLogin.performClick();
        super.onBackPressed();
    }

    @Override
    public void postResult(ResponseEntity<RegisterResponseModel> responseEntity) {
        Log.i("Register","Response: " + responseEntity.getBody());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            RegisterResponseModel registerResponseModel = responseEntity.getBody();
            Toast.makeText(
                    this,
                    " Register successfully",
                    Toast.LENGTH_LONG
            ).show();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(
                    this,
                    "Error code: " + responseEntity.getStatusCode().toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}