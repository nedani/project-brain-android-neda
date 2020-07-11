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
import com.neda.project_brain_android_neda.form.RegisterForm;
import com.neda.project_brain_android_neda.model.RegisterResponseModel;
import com.neda.project_brain_android_neda.util.InternetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    public void onBackPressed() {
        btnLogin.performClick();
        super.onBackPressed();
    }

    private void callRegisterApi() {
        if (!InternetUtil.isInternetAvailable(RegisterActivity.this)) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "brain/signup";

        RegisterForm registerForm = new RegisterForm();
        registerForm.setEmail(edtEmail.getText().toString());
        registerForm.setPassword(edtPassword.getText().toString());
        registerForm.setFirstname(edtFirstname.getText().toString());
        registerForm.setLastname(edtLastname.getText().toString());
        registerForm.setUsername(edtUsername.getText().toString());

        final Gson gson = new GsonBuilder().create();
        JSONObject request = null;
        try {
            request = new JSONObject(gson.toJson(registerForm));

            Log.i("Request","Request: " + request);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("onResponse", "" + response.toString());

                    RegisterResponseModel registerResponseMode = gson.fromJson(response.toString(), RegisterResponseModel.class);

                    Toast.makeText(
                            RegisterActivity.this,
                            " Register successfully",
                            Toast.LENGTH_LONG
                    ).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(
                            RegisterActivity.this,
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