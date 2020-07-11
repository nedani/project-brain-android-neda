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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neda.project_brain_android_neda.MyApplication;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.form.ToDoForm;
import com.neda.project_brain_android_neda.model.UserIdeasModel;
import com.neda.project_brain_android_neda.util.InternetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserIdeasAdapter extends RecyclerView.Adapter<UserIdeasAdapter.ViewHolder> {

    private ArrayList<UserIdeasModel.Datum> arrayUserIdeas;
    private Context context;

    public UserIdeasAdapter(Context context, ArrayList<UserIdeasModel.Datum> arrayUserIdeas) {
        this.arrayUserIdeas = arrayUserIdeas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_user_idea_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserIdeasModel.Datum userIdeaModel = arrayUserIdeas.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());
        //holder.txtPostedBy.setText("Posted By: Human");

        holder.txtCite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.txtToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        holder.txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiForDeleteIdea("" + userIdeaModel.getId(), position);
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
        public TextView txtDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContext = (TextView) itemView.findViewById(R.id.txtContext);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            this.txtPostedBy = (TextView) itemView.findViewById(R.id.txtPostedBy);
            this.txtCite = (TextView) itemView.findViewById(R.id.txtCite);
            this.txtToDo = (TextView) itemView.findViewById(R.id.txtToDo);
            this.txtFollow = (TextView) itemView.findViewById(R.id.txtFollow);
            this.txtDelete = (TextView) itemView.findViewById(R.id.txtDelete);
        }
    }

    private void callApiForDeleteIdea(String ideaId, final int position) {
        if (!InternetUtil.isInternetAvailable(context)) {
            return;
        }

        String url = MyApplication.getInstance().getBaseUrl() + "idea/remove_idea?id=" + ideaId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.i("onResponse", "" + response.toString());

                arrayUserIdeas.remove(position);
                notifyDataSetChanged();

                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                context,
                                "Error Message: " + error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });


        MyApplication.getInstance().getRequestQueue().add(stringRequest);
    }

}