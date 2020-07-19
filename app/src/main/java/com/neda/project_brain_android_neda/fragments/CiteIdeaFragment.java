package com.neda.project_brain_android_neda.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.form.NewIdea;
import com.neda.project_brain_android_neda.interfaces.UpdateData;
import com.neda.project_brain_android_neda.util.InternetUtil;
import com.neda.project_brain_android_neda.util.SharedPrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CiteIdeaFragment extends Fragment implements View.OnClickListener {

    private TextView txtTitle;

    private EditText edtTitle;
    private EditText edtContext;
    private EditText edtContent;

    private Button btnSubmit;

    private SharedPrefsUtil sharedPrefsUtil;

    private String citeId = "";
    private String context = "";

    private UpdateData updateData;

    public static CiteIdeaFragment newInstance(String citeId, String context, UpdateData updateData) {
        CiteIdeaFragment frag = new CiteIdeaFragment();
        frag.citeId = citeId;
        frag.context = context;
        frag.updateData = updateData;
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
        sharedPrefsUtil = new SharedPrefsUtil(getActivity());

        txtTitle = view.findViewById(R.id.txtTitle);

        edtTitle = view.findViewById(R.id.edtTitle);
        edtContext = view.findViewById(R.id.edtContext);
        edtContent = view.findViewById(R.id.edtContent);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Set Title
        txtTitle.setText(R.string.new_idea);
        edtContext.setText("" + context);

        edtContext.setEnabled(false);
        edtContext.setTextColor(getActivity().getColor(R.color.citeLinkColor));

        btnSubmit.setOnClickListener(this);
    }

    private void checkValidation() {
        if (edtTitle.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_title, Toast.LENGTH_SHORT).show();

        } else if (edtContext.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_context, Toast.LENGTH_SHORT).show();

        } else if (edtContent.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_content, Toast.LENGTH_SHORT).show();

        } else {
            callApiToSubmitIdea();
        }
    }

    private void callApiToSubmitIdea() {
        if (!InternetUtil.isInternetAvailable(getActivity())) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "relation/save_idea_brain";

        NewIdea newIdea = new NewIdea();
        newIdea.setCiteId(citeId);
        newIdea.setUsername("" + sharedPrefsUtil.get("username"));
        newIdea.setTitle("" + edtTitle.getText().toString());
        newIdea.setContent("" + edtContent.getText().toString());
        newIdea.setContext("" + edtContext.getText().toString());

        Gson gson = new GsonBuilder().create();
        JSONObject request = null;
        try {
            request = new JSONObject(gson.toJson(newIdea));

            Log.i("Request","Request: " + request);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("onResponse", "" + response.toString());

                    edtTitle.setText("");
                    edtContext.setText("");
                    edtContent.setText("");

                    Toast.makeText(
                            getActivity(),
                            "Submitted successfully",
                            Toast.LENGTH_LONG
                    ).show();

                    if (updateData != null) {
                        updateData.updateData();
                    }

                    getActivity().getSupportFragmentManager().beginTransaction().remove(CiteIdeaFragment.this).commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            MyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                checkValidation();
                break;
        }
    }
}
