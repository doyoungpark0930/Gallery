package com.example.myloginapp.Description;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myloginapp.R;

public class Description extends AppCompatActivity {

    //리사이클러뷰로가자






    ImageView imageView;
    TextView NameText;
    TextView TimeText;
    TextView InfoText;


    Bitmap ArtImage; //이미지
    String ArtName; //이름
    String ArtTime; //시작날짜 ,종료날짜,가격 정보
    String ArtInfo; //전시정보
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        imageView=findViewById(R.id.descImageView);
        NameText=findViewById(R.id.artTitle);
        TimeText =findViewById(R.id.artTime);
        InfoText=findViewById(R.id.artInfo);


        Intent intent = getIntent(); //액티비티전환해서 넘어올때 해당 intent를 받는다.

        if(intent.getExtras()!=null){ //Description.java로 액티비티전환시 넘길때 액티비티의 값을 받음
            ArtImage=byte2Bitmap(intent.getByteArrayExtra("Image"));
            ArtName=(String)intent.getSerializableExtra("Name");
            ArtTime=(String) intent.getSerializableExtra("ArtTime");
            ArtInfo=(String) intent.getSerializableExtra("ArtInfo");

            imageView.setImageBitmap(ArtImage);
            NameText.setText(ArtName);
            TimeText.setText(ArtTime);
            InfoText.setText(ArtInfo);
        }





    }
    private Bitmap byte2Bitmap(byte[] data) { //byte값을 Bitmap으로 변환해주는 메소드
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

}
