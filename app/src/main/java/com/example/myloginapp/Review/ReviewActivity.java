package com.example.myloginapp.Review;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myloginapp.DesReviewInfo;
import com.example.myloginapp.GalleryLoader;
import com.example.myloginapp.Home.HomeActivity;
import com.example.myloginapp.LoginActivity;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.ReviewInsertLoader;
import com.example.myloginapp.SearchingGallery;
import com.example.myloginapp.SignupActivity;
import com.google.android.material.textfield.TextInputEditText;

//SignupActivity.IP_ADDRESS;
public class ReviewActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;

    ImageView imageView;

    int position;

    TextView NameText;
    RatingBar ratingBar;
    TextInputEditText review;
    Button register;
    Button cancel;

    SearchingGallery.CustomAdapter customAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_review);

        imageView= (ImageView) findViewById(R.id.gallery_image);
        NameText= (TextView) findViewById(R.id.gallery_name);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar) ;
        register=(Button) findViewById(R.id.submit);
        cancel=(Button) findViewById(R.id.cancel);
        review=(TextInputEditText) findViewById(R.id.review);
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

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ; //이거 추가좀...
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //rating bar의 숫자로 추가예정...
                ReviewInsertLoader Loader= new ReviewInsertLoader();
                Object.review.add(new DesReviewInfo(Object.user.getNum(),Object.art.get(position).getNum(), review.getText().toString(),0));
                Loader.execute("http://3.95.135.160/review.php", Integer.toString(Object.user.getNum()),Integer.toString(Object.art.get(position).getNum()), review.getText().toString(),Integer.toString(0));

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