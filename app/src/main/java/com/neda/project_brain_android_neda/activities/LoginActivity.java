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
import com.neda.project_brain_android_neda.form.LoginForm;
import com.neda.project_brain_android_neda.form.RegisterForm;
import com.neda.project_brain_android_neda.model.LoginResponseModel;
import com.neda.project_brain_android_neda.model.RegisterResponseModel;
import com.neda.project_brain_android_neda.rest.PostTaskJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ApiCallBackPost<LoginResponseModel> {

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
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail(edtEmail.getText().toString());
        loginForm.setPassword(edtPassword.getText().toString());
        new PostTaskJson<LoginForm, LoginResponseModel>(LoginResponseModel.class, this).execute(loginForm);
    }

    @Override
    public void postResult(ResponseEntity<LoginResponseModel> responseEntity) {
        Log.i("Login","Response: " + responseEntity.getBody());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LoginResponseModel loginResponseModel = responseEntity.getBody();
            Toast.makeText(
                    this,
                    " LoggedIn successfully",
                    Toast.LENGTH_LONG
            ).show();

            startActivity(new Intent(this, HomeActivity.class));
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