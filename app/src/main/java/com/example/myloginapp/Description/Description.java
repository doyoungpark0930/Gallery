package com.example.myloginapp.Description;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.Review.ReviewActivity;
import com.example.myloginapp.DesReviewInfo;

import java.util.ArrayList;

public class Description extends AppCompatActivity {


    ArrayList<DesReviewInfo> arrayList;
    DesReviewAdapter desReviewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    Bitmap ArtImage; //이미지
    String ArtName; //이름
    String ArtTime; //시작날짜 ,종료날짜,가격 정보
    String ArtInfo; //전시정보
    ArrayList<DesReviewInfo> ReviewList;

    ImageView imageView;
    TextView NameText;
    TextView TimeText;
    TextView InfoText;
    Button button;

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



            //static으로 선언된 Galleryinfo객체를 이름이같은것 등으로 탐색을 시도한다면, 계산하는데 오래걸릴것같아 ,클릭하면 putExtra로 해당 뷰의 객체를 넘겨받는 식으로 구현

            ArtImage=byte2Bitmap(intent.getByteArrayExtra("Image"));
            ArtName=(String)intent.getSerializableExtra("Name");
            ArtTime=(String) intent.getSerializableExtra("ArtTime");
            ArtInfo=(String) intent.getSerializableExtra("ArtInfo");
            ReviewList=(ArrayList<DesReviewInfo>) intent.getSerializableExtra("ReviewList");
            imageView.setImageBitmap(ArtImage);
            NameText.setText(ArtName);
            TimeText.setText(ArtTime);
            InfoText.setText(ArtInfo);

            arrayList= ReviewList; //넘겨줄때 해당 object.art의 getDesReviewInfo를 넘겨줘야함


        }

        //밑에는 리사이클러뷰 관련
        recyclerView=(RecyclerView) findViewById(R.id.dec_review); //description.xml에서따옴
        linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        desReviewAdapter=new DesReviewAdapter(arrayList);
        recyclerView.setAdapter(desReviewAdapter);

        button = (Button) findViewById(R.id.review_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Description.this, ReviewActivity.class);
                startActivity(intent);
            }
        });


    }


    private Bitmap byte2Bitmap(byte[] data) { //byte값을 Bitmap으로 변환해주는 메소드
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
