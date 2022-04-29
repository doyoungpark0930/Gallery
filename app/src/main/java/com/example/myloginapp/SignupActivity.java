package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MaterialButton signupCompletebtn = (MaterialButton) findViewById(R.id.signupCompletebtn);

        signupCompletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeSignUp();
            }
        });
    }

    void completeSignUp(){
        finish();
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
