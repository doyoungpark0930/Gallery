package com.example.myloginapp.HelperClasses.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.Object;
import com.example.myloginapp.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_review_desgin, parent, false);

        ReviewAdapter.ReviewHolder featuredViewHolder = new ReviewAdapter.ReviewHolder(view);

        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.title.setText(Object.art.get(2).getName());
        holder.desc.setText(Object.art.get(2).getDesc());
    }

    @Override
    public int getItemCount() {
        return Object.art.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{
        public TextView title, desc;

        public ReviewHolder(@NonNull View view){
            super(view);

            title = (TextView) view.findViewById(R.id.review_title);
            desc = (TextView) view.findViewById(R.id.review_description);
        }
    }
}
