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


        StringBuilder ArtTime;
        String getArtTime;
        byte[] ImageBt;
        String ArtName;
        String ArtDsc;

        public Holder(@NonNull View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition(); //클릭된 현재 뷰의 position

                    ArtTime=(StringBuilder)  Object.art.get(position).PrintArt(); //Art시간을 받는다
                    getArtTime=ArtTime.toString(); //StringBuilder라서 직렬화가 가능한 string으로 바꿔줌
                    ImageBt=bitmap2Bytes(Object.art.get(position).getImage()); //bitmap인 이미지를 byte값으로 변환.직렬화를 위해
                    ArtName=(String)Object.art.get(position).getName();     //Art이름을 받는다
                    ArtDsc=(String)Object.art.get(position).getDesc();  //Art설명을 받는다

                    Intent intent=new Intent(v.getContext(),Description.class); //프래그먼트에서 액티비티로 화면전환할때는 HomeFragment가아닌 context로 받아야한다.
                    //밑에와 같이 putExtra로 값을 전달하고 Description.java에서 getExtra로 값을 받는다
                    intent.putExtra("ArtTime",getArtTime);
                    intent.putExtra("Image",ImageBt); //일단 byte값을 넘기고 getExtra에서 byte를 bitmap으로 다시 변환
                    intent.putExtra("Name",ArtName);
                    intent.putExtra("ArtInfo",ArtDsc);
                    v.getContext().startActivity(intent);
                }
                private byte[] bitmap2Bytes(Bitmap bitmap) { //Bitmap을 byte형식으로 바꿔주는 메소드
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    return baos.toByteArray();
                }
            });


            image = (ImageView) view.findViewById(R.id.exhibition_image);
            title = (TextView) view.findViewById(R.id.exhibition_title);
            desc = (TextView) view.findViewById(R.id.exhibition_description);

        }
    }


}
