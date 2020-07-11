package com.neda.project_brain_android_neda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.form.LoginForm;
import com.neda.project_brain_android_neda.form.RegisterForm;
import com.neda.project_brain_android_neda.model.LoginResponseModel;
import com.neda.project_brain_android_neda.util.InternetUtil;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;
    private Button btnRegister;
    private EditText edtEmail;
    private EditText edtPassword;

    private SharedPrefsUtil sharedPrefsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        sharedPrefsUtil = new SharedPrefsUtil(LoginActivity.this);

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
        if (!InternetUtil.isInternetAvailable(LoginActivity.this)) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "brain/signin";

        LoginForm loginForm = new LoginForm();
        loginForm.setEmail(edtEmail.getText().toString());
        loginForm.setPassword(edtPassword.getText().toString());

        final Gson gson = new GsonBuilder().create();
        JSONObject request = null;
        try {
            request = new JSONObject(gson.toJson(loginForm));

            Log.i("Request","Request: " + request);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("onResponse", "" + response.toString());

                    LoginResponseModel loginResponseModel = gson.fromJson(response.toString(), LoginResponseModel.class);

                    Toast.makeText(
                            LoginActivity.this,
                            " LoggedIn successfully",
                            Toast.LENGTH_LONG
                    ).show();

                    sharedPrefsUtil.saveLoginData(loginResponseModel.getUsername(),
                            loginResponseModel.getFirstname(), loginResponseModel.getLastname(), loginResponseModel.getEmail());

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Error: " + error.networkResponse,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}