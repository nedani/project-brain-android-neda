package com.neda.project_brain_android_neda.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.activities.RegisterActivity;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;
    private Button btnUpdate;

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
        txtTitle = view.findViewById(R.id.txtTitle);
        imgBack = view.findViewById(R.id.imgBack);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        imgBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        // Set Title
        txtTitle.setText(R.string.update_profile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;

            case R.id.btnUpdate:
                imgBack.performClick();
                break;
        }
    }
}
