package com.example.myloginapp.HelperClasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.Description.Description;
import com.example.myloginapp.Home.HomeFragment;
import com.example.myloginapp.R;
import com.example.myloginapp.Object;
import com.example.myloginapp.SearchingGallery;

import java.io.ByteArrayOutputStream;

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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition(); //클릭된 현재 뷰의 position
                    Intent intent=new Intent(v.getContext(),Description.class); //프래그먼트에서 액티비티로 화면전환할때는 HomeFragment가아닌 context로 받아야한다.
                    //밑에와 같이 putExtra로 값을 전달하고 Description.java에서 getExtra로 값을 받는다
                    intent.putExtra("ObjectPosition",position);
                    v.getContext().startActivity(intent);
                }

            });


            image = (ImageView) view.findViewById(R.id.exhibition_image);
            title = (TextView) view.findViewById(R.id.exhibition_title);
            desc = (TextView) view.findViewById(R.id.exhibition_description);

        }
    }


}