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

import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.activities.HomeActivity;
import com.neda.project_brain_android_neda.callback.ApiCallBackPost;
import com.neda.project_brain_android_neda.form.LoginForm;
import com.neda.project_brain_android_neda.form.UpdateProfileForm;
import com.neda.project_brain_android_neda.model.LoginResponseModel;
import com.neda.project_brain_android_neda.rest.PostTaskJson;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener, ApiCallBackPost<LoginResponseModel> {

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
        if (edtPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_password, Toast.LENGTH_SHORT).show();

        } else if (edtFirstname.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_firstname, Toast.LENGTH_SHORT).show();

        } else if (edtLastname.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_lastname, Toast.LENGTH_SHORT).show();

        } else {
            callUserUpdateProfileApi();
        }
    }

    private void callUserUpdateProfileApi() {
        UpdateProfileForm updateProfileForm = new UpdateProfileForm();
        updateProfileForm.setUsername(edtUsername.getText().toString());
        updateProfileForm.setFirstname(edtFirstname.getText().toString());
        updateProfileForm.setLastname(edtLastname.getText().toString());
        new PostTaskJson<UpdateProfileForm, LoginResponseModel>(LoginResponseModel.class, this).execute(updateProfileForm);
    }

    @Override
    public void postResult(ResponseEntity<LoginResponseModel> responseEntity) {
        Log.i("postResult","Response: " + responseEntity.getBody());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LoginResponseModel loginResponseModel = responseEntity.getBody();
            Toast.makeText(
                    getActivity(),
                    " Update successfully",
                    Toast.LENGTH_LONG
            ).show();

            //Remove Fragment
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

            sharedPrefsUtil.saveLoginData(loginResponseModel.getUsername(),
                    loginResponseModel.getFirstname(), loginResponseModel.getLastname(), loginResponseModel.getEmail());
        } else {
            Toast.makeText(
                    getActivity(),
                    "Error code: " + responseEntity.getStatusCode().toString(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
