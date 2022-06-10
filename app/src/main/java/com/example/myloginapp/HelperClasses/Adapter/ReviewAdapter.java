package com.example.myloginapp.HelperClasses.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.DesReviewInfo;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    ArrayList<DesReviewInfo> review;
    public ReviewAdapter(ArrayList<DesReviewInfo> review){
        this.review=review;
    }
    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_review_desgin, parent, false);

        ReviewAdapter.ReviewHolder featuredViewHolder = new ReviewAdapter.ReviewHolder(view);

        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.title.setText(review.get(position).getTitle()); //해당 뷰 객체에 art이름 일단 넣은 것,따라서 리뷰 갯수는 전시 작품의 총 개수만큼 할당 됨. 그럼 수영님이 여기에 구독한 작품에 해당하는 리뷰 값을 넣어야함.
        holder.desc.setText(review.get(position).getEvaluation());
    }

    @Override
    public int getItemCount() {
        return Object.review.size();
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
