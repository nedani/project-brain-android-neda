package com.neda.project_brain_android_neda.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.form.UpdateProfileForm;
import com.neda.project_brain_android_neda.model.LoginResponseModel;
import com.neda.project_brain_android_neda.util.InternetUtil;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;
    private Button btnUpdate;
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtFirstname;
    private EditText edtLastname;

    private SharedPrefsUtil sharedPrefsUtil;

    public static UpdateProfileFragment newInstance() {
        UpdateProfileFragment frag = new UpdateProfileFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        sharedPrefsUtil = new SharedPrefsUtil(getActivity());

        txtTitle = view.findViewById(R.id.txtTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtFirstname = view.findViewById(R.id.edtFirstname);
        edtLastname = view.findViewById(R.id.edtLastname);

        imgBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        // Set Title
        txtTitle.setText(R.string.update_profile);

        edtUsername.setText("" + sharedPrefsUtil.getUsername());
        edtEmail.setText("" + sharedPrefsUtil.get("email"));
        edtFirstname.setText("" + sharedPrefsUtil.get("firstname"));
        edtLastname.setText("" + sharedPrefsUtil.get("lastname"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;

            case R.id.btnUpdate:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        if (edtFirstname.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_firstname, Toast.LENGTH_SHORT).show();

        } else if (edtLastname.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_lastname, Toast.LENGTH_SHORT).show();

        } else {
            callUserUpdateProfileApi();
        }
    }

    private void callUserUpdateProfileApi() {
        if (!InternetUtil.isInternetAvailable(getActivity())) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "brain/update_brain";

        UpdateProfileForm updateProfileForm = new UpdateProfileForm();
        updateProfileForm.setUsername(edtUsername.getText().toString());
        updateProfileForm.setFirstname(edtFirstname.getText().toString());
        updateProfileForm.setLastname(edtLastname.getText().toString());

        final Gson gson = new GsonBuilder().create();
        JSONObject request = null;
        try {
            request = new JSONObject(gson.toJson(updateProfileForm));

            Log.i("Request","Request: " + request);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("onResponse", "" + response.toString());

                    LoginResponseModel loginResponseModel = gson.fromJson(response.toString(), LoginResponseModel.class);

                    Toast.makeText(
                            getActivity(),
                            " Update successfully",
                            Toast.LENGTH_LONG
                    ).show();

                    //Remove Fragment
                    getActivity().getSupportFragmentManager().beginTransaction().remove(UpdateProfileFragment.this).commit();

                    sharedPrefsUtil.saveLoginData(loginResponseModel.getUsername(),
                            loginResponseModel.getFirstname(), loginResponseModel.getLastname(), loginResponseModel.getEmail());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(
                            getActivity(),
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
