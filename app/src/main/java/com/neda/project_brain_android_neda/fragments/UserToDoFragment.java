package com.neda.project_brain_android_neda.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.adapters.TodoAdapter;
import com.neda.project_brain_android_neda.model.UserITodoModel;
import com.neda.project_brain_android_neda.model.UserIdeasModel;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import java.util.ArrayList;

public class UserToDoFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;

    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;

    private SharedPrefsUtil sharedPrefsUtil;

    private ArrayList<UserITodoModel.Datum> arrayUserTodos = new ArrayList<>();

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

        callApiToGetUserTodos();
    }

    private void initList() {
        sharedPrefsUtil = new SharedPrefsUtil(getActivity());

        todoAdapter = new TodoAdapter(arrayUserTodos);

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

    private void callApiToGetUserTodos() {
        String url = "http://192.168.0.158:8080/brain/" + sharedPrefsUtil.getUsername() + "/todos";

        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Log.i("onResponse", "" + response.toString());

                        Gson gson = new GsonBuilder().create();
                        UserITodoModel userITodoModel = gson.fromJson(response.toString(), UserITodoModel.class);

                        arrayUserTodos.addAll(userITodoModel.getData());

                        todoAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getActivity(),
                                "Error Message: " + error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });


        MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);
    }
}
