package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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