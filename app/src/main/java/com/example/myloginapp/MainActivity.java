package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myloginapp.Home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 임시로 정렬을 확인하기 위해 사용함
        // 이미지 로더 부분
        GalleryLoader Loader = new GalleryLoader();
        Loader.execute("http://"+LoginActivity.IP_ADDRESS+"/ArtSelect.php", "");
        Button getStarted =(Button) findViewById(R.id.getStarted);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginActivity(); //getStarted버튼 클릭 시 LoginActivity로 이동.
                //AndroidManifest에 <activity android:name=".LoginActivity"></activity> 추가해줘야함
                //나머지 다른 activity도 추가할 때 androidManifest에 추가하기, 안하면 클릭시 앱 종료됨
            }
        });


    }


    void navigateToLoginActivity(){ //LoginActivity로 가는 함수
        finish();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}