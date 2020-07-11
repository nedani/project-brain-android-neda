package com.neda.project_brain_android_neda.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.activities.LoginActivity;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;

    private Button btnUpdateProfile;
    private Button btnLogout;
    private Button btnUserIdea;
    private Button btnUserToDo;

    private SharedPrefsUtil sharedPrefsUtil;

    public static ProfileFragment newInstance() {
        ProfileFragment frag = new ProfileFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        sharedPrefsUtil = new SharedPrefsUtil(getActivity());

        txtTitle = view.findViewById(R.id.txtTitle);

        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnUserIdea = view.findViewById(R.id.btnUserIdea);
        btnUserToDo = view.findViewById(R.id.btnUserToDo);

        // Set Title
        txtTitle.setText(R.string.profile);

        btnUpdateProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnUserIdea.setOnClickListener(this);
        btnUserToDo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUserIdea:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,
                        UserIdeasFragment.newInstance(), UserIdeasFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnUpdateProfile:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,
                        UpdateProfileFragment.newInstance(), UpdateProfileFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnUserToDo:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,
                        UserToDoFragment.newInstance(), UserToDoFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnLogout:
                sharedPrefsUtil.clear();

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
