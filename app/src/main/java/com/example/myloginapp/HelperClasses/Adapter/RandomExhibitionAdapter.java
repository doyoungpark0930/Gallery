package com.example.myloginapp.HelperClasses.Adapter;

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



import com.example.myloginapp.DesReviewInfo;


import com.example.myloginapp.Description.Description;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.SearchingGallery;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomExhibitionAdapter extends RecyclerView.Adapter<RandomExhibitionAdapter.Holder>{
    static List<Integer> list;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);

        RandomExhibitionAdapter.Holder featuredViewHolder = new RandomExhibitionAdapter.Holder(view);

        return featuredViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        list = RandNum();
        Log.v("tag",Object.art.get((Integer) list.get(3)).getName());
        holder.image.setImageBitmap(Object.art.get((Integer) list.get(position)).getImage());
        holder.title.setText(Object.art.get((Integer) list.get(position)).getName());
        holder.desc.setText(Object.art.get((Integer) list.get(position)).getDesc());
        System.out.println(list);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class Holder extends RecyclerView.ViewHolder{
        public TextView title, desc;
        public ImageView image;

        StringBuilder ArtTime;
        String getArtTime;
        byte[] ImageBt;
        String ArtName;
        String ArtDsc;
        ArrayList<DesReviewInfo> ReviewList; //해당 뷰 객체의 리뷰리스트

        public Holder(@NonNull View view){
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition(); //클릭된 현재 뷰의 position. 4번쨰클릭하면 Object.art.get(4)가들어가는데, 그렇게 들어가지않고 해당 들어간 랜덤값의 position이 들어가야함

                    Intent intent=new Intent(v.getContext(),Description.class); //프래그먼트에서 액티비티로 화면전환할때는 HomeFragment가아닌 context로 받아야한다.
                    //밑에와 같이 putExtra로 값을 전달하고 Description.java에서 getExtra로 값을 받는다
                    intent.putExtra("ObjectPosition",Object.art.get(list.get(position).intValue()));
                    v.getContext().startActivity(intent);
                }
                private byte[] bitmap2Bytes(Bitmap bitmap) { //Bitmap을 byte형식으로 바꿔주는 메소드
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    return baos.toByteArray();
                }

            });


            //hooks
            image = (ImageView) view.findViewById(R.id.exhibition_image);
            title = (TextView) view.findViewById(R.id.exhibition_title);
            desc = (TextView) view.findViewById(R.id.exhibition_description);
        }
    }

    public List RandNum (){
        Set<Integer> set = new HashSet<>();
        while (set.size() < 10) {
            Double d = Math.random() * Object.art.size() + 1;
            set.add(d.intValue());
        }

        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        System.out.println(list);
        return list;
    }
}
