package com.example.myloginapp.Review;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myloginapp.Home.HomeActivity;
import com.example.myloginapp.LoginActivity;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.SearchingGallery;

public class ReviewActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;

    ImageView imageView;

    int position;

    TextView NameText;
    RatingBar ratingBar;

    SearchingGallery.CustomAdapter customAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_review);

        imageView= (ImageView) findViewById(R.id.gallery_image);
        NameText= (TextView) findViewById(R.id.gallery_name);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar) ;

        Intent intent = getIntent();

        if(intent.getExtras()!=null){
            position=(int)intent.getSerializableExtra("ObjectPosition");
            System.out.println("position"+ position);
            imageView.setImageBitmap(Object.art.get(position).getImage());
            NameText.setText(Object.art.get(position).getName());
        }


        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ReviewActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("프로그램 종료");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("프로그램을 종료할 것입니까?")
                        .setCancelable(false)
                        .setPositiveButton("종료",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 프로그램을 종료한다
//                                        AlertDialogActivity.this.finish();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();

            }
        });
    }

    public void showDialog(){
        final AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(ReviewActivity.this);
        final RatingBar rate = new RatingBar(this);

        myAlertBuilder.setTitle("로그인이 필요한 기능입니다.");
        myAlertBuilder.setView(rate);
        rate.setMax(5);

        myAlertBuilder.create();
        myAlertBuilder.show();
    }
}