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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomExhibitionAdapter extends RecyclerView.Adapter<RandomExhibitionAdapter.Holder>{

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);

        RandomExhibitionAdapter.Holder featuredViewHolder = new RandomExhibitionAdapter.Holder(view);

        return featuredViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        List list = RandNum();

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

        public Holder(@NonNull View view){
            super(view);

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
