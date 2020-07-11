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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.activities.RegisterActivity;
import com.neda.project_brain_android_neda.adapters.IdeasAdapter;
import com.neda.project_brain_android_neda.adapters.UserIdeasAdapter;
import com.neda.project_brain_android_neda.callback.ApiCallBackGet;
import com.neda.project_brain_android_neda.callback.ApiCallBackPost;
import com.neda.project_brain_android_neda.form.GetUserIdeasForm;
import com.neda.project_brain_android_neda.form.UpdateProfileForm;
import com.neda.project_brain_android_neda.model.LoginResponseModel;
import com.neda.project_brain_android_neda.model.UserIdeasModel;
import com.neda.project_brain_android_neda.rest.GetTaskJson;
import com.neda.project_brain_android_neda.rest.PostTaskJson;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class UserIdeasFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;

    private RecyclerView recyclerView;
    private UserIdeasAdapter userIdeasAdapter;

    private SharedPrefsUtil sharedPrefsUtil;

    private ArrayList<UserIdeasModel.Datum> arrayUserIdeas = new ArrayList<>();

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
        sharedPrefsUtil = new SharedPrefsUtil(getActivity());

        txtTitle = view.findViewById(R.id.txtTitle);
        imgBack = view.findViewById(R.id.imgBack);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set Title
        txtTitle.setText(R.string.user_ideas);

        imgBack.setOnClickListener(this);

        initList();

        callApiToGetUserIdeas();
    }

    private void initList() {
        userIdeasAdapter = new UserIdeasAdapter(arrayUserIdeas);

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

    private void callApiToGetUserIdeas() {
        String url = "http://192.168.0.158:8080/brain/" + sharedPrefsUtil.getUsername() + "/ideas";

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
                        UserIdeasModel userIdeasModel = gson.fromJson(response.toString(), UserIdeasModel.class);

                        arrayUserIdeas.addAll(userIdeasModel.getData());

                        userIdeasAdapter.notifyDataSetChanged();
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
