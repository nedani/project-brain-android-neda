package com.neda.project_brain_android_neda.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neda.project_brain_android_neda.R;

public class NewIdeaFragment extends Fragment {

    private TextView txtTitle;

    public static NewIdeaFragment newInstance() {
        NewIdeaFragment frag = new NewIdeaFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_idea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        txtTitle = view.findViewById(R.id.txtTitle);

        // Set Title
        txtTitle.setText(R.string.new_idea);
    }
}
