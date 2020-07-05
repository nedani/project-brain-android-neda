package com.neda.project_brain_android_neda.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.adapters.IdeasAdapter;
import com.neda.project_brain_android_neda.adapters.UserIdeasAdapter;

public class UserIdeasFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;

    private RecyclerView recyclerView;
    UserIdeasAdapter userIdeasAdapter;


    public static UserIdeasFragment newInstance() {
        UserIdeasFragment frag = new UserIdeasFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_idea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        txtTitle = view.findViewById(R.id.txtTitle);
        imgBack = view.findViewById(R.id.imgBack);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set Title
        txtTitle.setText(R.string.user_ideas);

        imgBack.setOnClickListener(this);

        initList();
    }

    private void initList() {
        userIdeasAdapter = new UserIdeasAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(userIdeasAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }
}
