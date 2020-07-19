package com.neda.project_brain_android_neda.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.activities.HomeActivity;
import com.neda.project_brain_android_neda.form.FollowForm;
import com.neda.project_brain_android_neda.form.NewIdea;
import com.neda.project_brain_android_neda.form.ToDoForm;
import com.neda.project_brain_android_neda.fragments.CiteIdeaFragment;
import com.neda.project_brain_android_neda.fragments.OriginalIdeaFragment;
import com.neda.project_brain_android_neda.fragments.UserIdeasFragment;
import com.neda.project_brain_android_neda.interfaces.UpdateData;
import com.neda.project_brain_android_neda.model.UserITodoModel;
import com.neda.project_brain_android_neda.model.UserIdeasModel;
import com.neda.project_brain_android_neda.util.InternetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.ViewHolder> {

    private ArrayList<UserIdeasModel.Datum> arrayUserIdeas;
    private String username;
    private Context context;
    private UpdateData updateData;

    public IdeasAdapter(Context context, ArrayList<UserIdeasModel.Datum> arrayUserIdeas, String username, UpdateData updateData) {
        this.context = context;
        this.arrayUserIdeas = arrayUserIdeas;
        this.username = username;
        this.updateData = updateData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_idea_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserIdeasModel.Datum userIdeaModel = arrayUserIdeas.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());

        if (!("" + userIdeaModel.getCiteId()).equals("null")) {
            holder.txtContext.setTextColor(((HomeActivity) context).getColor(R.color.citeLinkColor));
        }

            holder.txtContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("" + userIdeaModel.getCiteId()).equals("null")) {
                    ((HomeActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.container,
                            OriginalIdeaFragment.newInstance("" + userIdeaModel.getCiteId()), OriginalIdeaFragment.class.getSimpleName()).commit();
                }
            }
        });

        if (userIdeaModel.getAuthor().getUsername().equals("" + username)) {
            holder.txtPostedBy.setText("Posted By: You");

            holder.txtCite.setVisibility(View.GONE);
            holder.txtToDo.setVisibility(View.GONE);
            holder.txtFollow.setVisibility(View.GONE);

        } else {
            holder.txtPostedBy.setText("Posted By: " + userIdeaModel.getAuthor().getUsername());

            holder.txtCite.setVisibility(View.VISIBLE);
            holder.txtToDo.setVisibility(View.VISIBLE);
            holder.txtFollow.setVisibility(View.VISIBLE);
        }

        holder.txtCite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.container,
                        CiteIdeaFragment.newInstance("" + userIdeaModel.getId(), "" + userIdeaModel.getTitle(), updateData), CiteIdeaFragment.class.getSimpleName()).commit();
            }
        });

        holder.txtToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiForToDo("" + userIdeaModel.getId());
            }
        });


        holder.txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiForFollowUser("" + userIdeaModel.getAuthor().getUsername());
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayUserIdeas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtContext;
        public TextView txtContent;
        public TextView txtPostedBy;
        public TextView txtCite;
        public TextView txtToDo;
        public TextView txtFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContext = (TextView) itemView.findViewById(R.id.txtContext);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            this.txtPostedBy = (TextView) itemView.findViewById(R.id.txtPostedBy);
            this.txtCite = (TextView) itemView.findViewById(R.id.txtCite);
            this.txtToDo = (TextView) itemView.findViewById(R.id.txtToDo);
            this.txtFollow = (TextView) itemView.findViewById(R.id.txtFollow);
        }
    }

    private void callApiForToDo(String ideaId) {
        if (!InternetUtil.isInternetAvailable(context)) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "relation/save_todo_brain";

        ToDoForm toDoForm = new ToDoForm();
        toDoForm.setUsername("" + username);
        toDoForm.setIdeaId("" + ideaId);

        Gson gson = new GsonBuilder().create();
        JSONObject request = null;
        try {
            request = new JSONObject(gson.toJson(toDoForm));

            Log.i("Request","Request: " + request);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("onResponse", "" + response.toString());

                    Toast.makeText(
                            context,
                            "Saved in Todo list successfully",
                            Toast.LENGTH_LONG
                    ).show();
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

    private void callApiForFollowUser(String toBeFollowedUsername) {
        if (!InternetUtil.isInternetAvailable(context)) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "brain/follow_brain";

        FollowForm followForm = new FollowForm();
        followForm.setUsername("" + username);
        followForm.setUsernameToBeFollowed("" + toBeFollowedUsername);

        Gson gson = new GsonBuilder().create();
        JSONObject request = null;
        try {
            request = new JSONObject(gson.toJson(followForm));

            Log.i("Request","Request: " + request);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.i("onResponse", "" + response.toString());

                    Toast.makeText(
                            context,
                            "Followed successfully",
                            Toast.LENGTH_LONG
                    ).show();
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

}