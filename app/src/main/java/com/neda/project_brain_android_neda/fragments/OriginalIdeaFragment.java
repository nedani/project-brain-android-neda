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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.model.SingleIdeaModel;
import com.neda.project_brain_android_neda.model.UserIdeasModel;
import com.neda.project_brain_android_neda.util.InternetUtil;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import java.util.ArrayList;

public class OriginalIdeaFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgBack;

    public TextView txtIdeaTitle;
    public TextView txtContext;
    public TextView txtContent;
    public TextView txtPostedBy;

    private String id = "";

    public static OriginalIdeaFragment newInstance(String id) {
        OriginalIdeaFragment frag = new OriginalIdeaFragment();
        frag.id = id;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_original_idea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        txtTitle = view.findViewById(R.id.txtTitle);
        imgBack = view.findViewById(R.id.imgBack);

        this.txtIdeaTitle = (TextView) view.findViewById(R.id.txtIdeaTitle);
        this.txtContext = (TextView) view.findViewById(R.id.txtContext);
        this.txtContent = (TextView) view.findViewById(R.id.txtContent);
        this.txtPostedBy = (TextView) view.findViewById(R.id.txtPostedBy);

        txtTitle.setText(R.string.original_idea);

        callApiToGetOriginalIdeas();

        imgBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

    private void callApiToGetOriginalIdeas() {
        if (!InternetUtil.isInternetAvailable(getActivity())) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "idea?id=" + id;

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
                        SingleIdeaModel singleIdeaModel = gson.fromJson(response.toString(), SingleIdeaModel.class);

                        txtIdeaTitle.setText("" + singleIdeaModel.getTitle());
                        txtContext.setText("" + singleIdeaModel.getContext());
                        txtContent.setText("" + singleIdeaModel.getContent());

                        txtPostedBy.setText("Posted By: " + singleIdeaModel.getAuthor().getUsername());
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
