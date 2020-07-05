package com.neda.project_brain_android_neda.fragments;

import android.content.Intent;
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
import com.neda.project_brain_android_neda.activities.RegisterActivity;
import com.neda.project_brain_android_neda.adapters.IdeasAdapter;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgSearch;

    private RecyclerView recyclerView;
    IdeasAdapter ideasAdapter;


    public static HomeFragment newInstance() {
        HomeFragment frag = new HomeFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        txtTitle = view.findViewById(R.id.txtTitle);
        imgSearch = view.findViewById(R.id.imgSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set Title
        txtTitle.setText(R.string.home);

        imgSearch.setOnClickListener(this);

        initList();
    }

    private void initList() {
        ideasAdapter = new IdeasAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ideasAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSearch:
                break;
        }
    }
}
