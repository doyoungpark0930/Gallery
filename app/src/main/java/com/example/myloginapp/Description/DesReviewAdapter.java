package com.example.myloginapp.Description;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.DesReviewInfo;

import java.util.ArrayList;

public class DesReviewAdapter extends RecyclerView.Adapter<DesReviewAdapter.CustomViewHolder> {

    private ArrayList<DesReviewInfo> arrayList;

    public DesReviewAdapter(ArrayList<DesReviewInfo> review) {
        this.arrayList = review;
    } //이거 수정

    @NonNull
    @Override
    public DesReviewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//리사이클러뷰가 처음 생성될때의 생명주기를 뜻함. 액티비티의 onCreate와 비슷

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_review_desgin,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DesReviewAdapter.CustomViewHolder holder, int position) {
        holder.ratingBar.setRating(arrayList.get(position).getStar());
        holder.ReviewTitle.setText(arrayList.get(position).getTitle());
        holder.ReviewEvaluation.setText(arrayList.get(position).getEvaluation());
        holder.ratingBar.setRating(arrayList.get(position).getStar());


    }
    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0); //리뷰의 개수만큼.. null값이라면 0반환으로 하기
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected RatingBar ratingBar;
        protected TextView ReviewTitle;
        protected TextView ReviewEvaluation;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ratingBar=(RatingBar) itemView.findViewById(R.id.ratingbar);
            this.ReviewTitle=(TextView) itemView.findViewById(R.id.review_title);
            this.ReviewEvaluation=(TextView) itemView.findViewById(R.id.review_description);
        }
    }
}
