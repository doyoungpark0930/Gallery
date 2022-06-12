package com.example.myloginapp.Description;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.DesReviewInfo;
import com.example.myloginapp.Home.HomeActivity;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.Review.ReviewActivity;

import java.util.ArrayList;

public class Description extends AppCompatActivity {


    ArrayList<DesReviewInfo> arrayList;
    public static DesReviewAdapter desReviewAdapter;
    public static ArrayList<DesReviewInfo> tmp;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    int position;

    Bitmap ArtImage; //이미지
    String ArtName; //이름
    String ArtTime; //시작날짜 ,종료날짜,가격 정보
    String ArtInfo; //전시정보
    ArrayList<DesReviewInfo> ReviewList;

    ImageView back2main;
    ImageView imageView;
    TextView NameText;
    TextView TimeText;
    TextView InfoText;
    Button button;
    ImageButton subscribeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        imageView=findViewById(R.id.descImageView);
        NameText=findViewById(R.id.artTitle);
        TimeText =findViewById(R.id.artTime);
        InfoText=findViewById(R.id.artInfo);

        subscribeButton=(ImageButton) findViewById(R.id.subscribeButton);


        Intent intent = getIntent(); //액티비티전환해서 넘어올때 해당 intent를 받는다.

        if(intent.getExtras()!=null){ //Description.java로 액티비티전환시 넘길때 액티비티의 값을 받음

           /*
            position=(int)intent.getSerializableExtra("ObjectPosition"); // position값받음

            imageView.setImageBitmap(Object.art.get(position).getImage());
            NameText.setText(Object.art.get(position).getName());
            TimeText.setText(Object.art.get(position).PrintArt());
            InfoText.setText(Object.art.get(position).getDesc());

            */





            ArtImage=byte2Bitmap(intent.getByteArrayExtra("Image"));
            ArtName=(String)intent.getSerializableExtra("Name");
            ArtTime=(String) intent.getSerializableExtra("ArtTime");
            ArtInfo=(String) intent.getSerializableExtra("ArtInfo");

            imageView.setImageBitmap(ArtImage);
            NameText.setText(ArtName);
            TimeText.setText(ArtTime);
            InfoText.setText(ArtInfo);


        }

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "구독 하셨습니다.", Toast.LENGTH_SHORT).show(); //로그인화면으로 왔을시 거기서 ID생성 토스트메시지 띄워줌
            }
        });

        //밑에는 리사이클러뷰 관련
        recyclerView=(RecyclerView) findViewById(R.id.dec_review); //description.xml에서따옴
        linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList= Object.art.get(position).getDesReviewInfo();

        tmp=new ArrayList<DesReviewInfo>();
        tmp.clear();
        for(DesReviewInfo i : Object.review){
            if(i.getArtnum()==Object.art.get(position).getNum()) {
                tmp.add(i);
            }
        }
        Log.e("art","tmp의 크기는?"+Integer.toString(tmp.size()));
        desReviewAdapter=new DesReviewAdapter(tmp);
        recyclerView.setAdapter(desReviewAdapter);
        Handler mHandler = new Handler();
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                // UI 작업 수행 X
                mHandler.post(new Runnable(){
                    @Override
                    public void run() {

                        desReviewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        t.start();

        button = (Button) findViewById(R.id.review_button);
        back2main = (ImageView) findViewById(R.id.back2main);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Description.this, ReviewActivity.class);
                intent.putExtra("ObjectPosition", position);
                startActivity(intent);
            }
        });

        back2main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Description.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    private Bitmap byte2Bitmap(byte[] data) { //byte값을 Bitmap으로 변환해주는 메소드
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }


}