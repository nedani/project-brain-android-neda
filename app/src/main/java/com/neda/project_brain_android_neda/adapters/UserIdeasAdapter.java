package com.neda.project_brain_android_neda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.neda.project_brain_android_neda.R;

public class UserIdeasAdapter extends RecyclerView.Adapter<UserIdeasAdapter.ViewHolder> {

    public UserIdeasAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.view_user_idea_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTitle.setText("Android Development");
        holder.txtContext.setText("Android Development for Final Semester");
        holder.txtContent.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. \\n\\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        holder.txtPostedBy.setText("Posted By: Human");

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
    }


    @Override
    public int getItemCount() {
        return 10;
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

}