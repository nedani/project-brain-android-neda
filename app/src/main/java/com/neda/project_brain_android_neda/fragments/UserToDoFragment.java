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
import com.neda.project_brain_android_neda.adapters.TodoAdapter;

public class UserToDoFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;

    private RecyclerView recyclerView;
    TodoAdapter todoAdapter;


    public static UserToDoFragment newInstance() {
        UserToDoFragment frag = new UserToDoFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_todo, container, false);
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
        txtTitle.setText(R.string.to_do);

        imgBack.setOnClickListener(this);

        initList();
    }

    private void initList() {
        todoAdapter = new TodoAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(todoAdapter);
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
