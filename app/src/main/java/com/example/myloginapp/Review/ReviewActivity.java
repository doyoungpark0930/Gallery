package com.example.myloginapp.Review;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myloginapp.Description.Description;
import com.example.myloginapp.GalleryLoader;
import com.example.myloginapp.Home.HomeActivity;
import com.example.myloginapp.LoginActivity;
import com.example.myloginapp.MainActivity;
import com.example.myloginapp.Object;
import com.example.myloginapp.R;
import com.example.myloginapp.ReviewInsertLoader;
import com.example.myloginapp.ReviewLoader;
import com.example.myloginapp.SearchingGallery;
import com.example.myloginapp.SignupActivity;
import com.google.android.material.textfield.TextInputEditText;

//SignupActivity.IP_ADDRESS;
public class ReviewActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    private static final String IP_ADDRESS = "3.95.135.160"; //현재 나의 ip번호 -> 서버로 변경할 예정임.

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
        /*
        if(Object.user==null){
            showDialog();
            Intent intent=new Intent(ReviewActivity.this, Description.class); //intent를 이용해 Activity전환
                //이렇게 putExtra로 값을 전달하고 Description.java에서 getExtra로 값을 받는다
                intent.putExtra("ObjectPosition",position);
                startActivity(intent);
            현재 로그인 부분 오류 고치면 이부분 주석 처리 빼고 사용.
        }*/
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
                Intent intent=new Intent(ReviewActivity.this, Description.class); //intent를 이용해 Activity전환
                //이렇게 putExtra로 값을 전달하고 Description.java에서 getExtra로 값을 받는다
                intent.putExtra("ObjectPosition",position);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //rating bar의 숫자로 추가예정...
                ReviewInsertLoader Loader= new ReviewInsertLoader();
                //로그인을 해야지만 할수 있도록 해야하는데..왜 안되는거지?
                // 변경해야할 사항
                //Loader.execute("http://"+IP_ADDRESS+"/review.php", Integer.toString(Object.user.getNum()),Integer.toString(Object.art.get(position).getNum()), review.getText().toString(),Integer.toString(rating));
                //
                Log.e("tag",review.getText().toString());
                Loader.execute("http://"+IP_ADDRESS+"/IReview.php", Integer.toString(1),Integer.toString(Object.art.get(position).getNum()), review.getText().toString(),Integer.toString(0));

                ReviewLoader RLoader= new ReviewLoader();
                RLoader.execute("http://"+IP_ADDRESS+"/review.php");

                //Description.desReviewAdapter.notifyItemInserted(Object.review.size());
                Log.e("tag",Integer.toString(Object.review.size()));

                finish();
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
    @Override
    public void onBackPressed(){

    }
}