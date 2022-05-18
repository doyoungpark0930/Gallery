package com.example.myloginapp.HelperClasses.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.R;
import com.example.myloginapp.Object;

public class ExhibitionViewAdapter extends RecyclerView.Adapter<ExhibitionViewAdapter.Holder>{

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);

        Holder featuredViewHolder = new Holder(view);

        return featuredViewHolder;
    }
/**/
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        /*이부분 수정해야함. 왜냐하면, 구독한 내용으로 변경해야하기 때문, 또한 반복문을 이용할시 하나의
        * 내용물만 반복하게 됨으로, 반복문을 밖에서 다른곳에서 선언해야할듯,,, 일단 수요일날 희진이랑
        * 상의 예정.*/

        holder.image.setImageBitmap(Object.art.get(position).getImage());
        holder.title.setText(Object.art.get(position).getName());
        holder.desc.setText(Object.art.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return Object.art.size();
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public static class Holder extends RecyclerView.ViewHolder{
        public TextView title, desc;
        public ImageView image;

        public Holder(@NonNull View view){
            super(view);

            //hooks
            image = (ImageView) view.findViewById(R.id.exhibition_image);
            title = (TextView) view.findViewById(R.id.exhibition_title);
            desc = (TextView) view.findViewById(R.id.exhibition_description);
        }
    }

}
