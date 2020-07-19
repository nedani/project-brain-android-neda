package com.neda.project_brain_android_neda.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.neda.project_brain_android_neda.adapters.IdeasAdapter;
import com.neda.project_brain_android_neda.model.UserIdeasModel;
import com.neda.project_brain_android_neda.util.InternetUtil;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import java.util.ArrayList;

public class SearchByTitleFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private TextView txtTitle;
    private TextView txtNoData;
    private ImageView imgSearch;

    private EditText edtSearch;

    private RecyclerView recyclerView;
    private IdeasAdapter ideasAdapter;

    private SharedPrefsUtil sharedPrefsUtil;

    private ArrayList<UserIdeasModel.Datum> arrayUserIdeas = new ArrayList<>();

    private boolean isFirstTime = true;

    public static SearchByTitleFragment newInstance() {
        SearchByTitleFragment frag = new SearchByTitleFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        sharedPrefsUtil = new SharedPrefsUtil(getActivity());

        txtTitle = view.findViewById(R.id.txtTitle);
        txtNoData = view.findViewById(R.id.txtNoData);
        imgSearch = view.findViewById(R.id.imgSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        edtSearch = view.findViewById(R.id.edtSearch);

        edtSearch.addTextChangedListener(this);

        // Set Title
        txtTitle.setText(R.string.search_by_title);

        imgSearch.setOnClickListener(this);
    }

    private void initList() {
        ideasAdapter = new IdeasAdapter(getActivity(), arrayUserIdeas, sharedPrefsUtil.getUsername(), null);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ideasAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSearch:
                String searchContent = "" + edtSearch.getText().toString().trim();
                if (searchContent.length() != 0) {

                    callApiToSearchIdea(searchContent);
                } else {
                    Toast.makeText(getActivity(),"No search keyword", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callApiToSearchIdea(String searchContent) {
        if (!InternetUtil.isInternetAvailable(getActivity())) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + searchContent + "/ideas";

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

                        if (arrayUserIdeas.size() > 0) {
                            arrayUserIdeas = new ArrayList<>();
                        }

                        arrayUserIdeas.addAll(userIdeasModel.getData());

                        if (arrayUserIdeas.size() == 0) {
                            txtNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            txtNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        initList();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isFirstTime && s.toString().trim().length() == 0) {
            recyclerView.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

        isFirstTime = false;
    }
}
