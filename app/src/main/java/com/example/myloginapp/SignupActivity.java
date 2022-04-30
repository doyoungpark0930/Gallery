package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG="SignupActivity"; //로그 찍기위한 TAG변수. 로그 안 찍어볼거면 무시해도됨


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MaterialButton signupCompletebtn = (MaterialButton) findViewById(R.id.signupCompletebtn);

        EditText Password =(EditText) findViewById(R.id.password);
        EditText ConfigPassword =(EditText) findViewById(R.id.configPassword);

        signupCompletebtn.setOnClickListener(new View.OnClickListener() { //밑에 OK버튼 클릭하면
            @Override
            public void onClick(View v) {
                String firstPassword=Password.getText().toString();
                String CheckPassword=ConfigPassword.getText().toString();

                if(firstPassword.length()<6) //패스워드 글자수가 6이하라면
                {
                    Toast.makeText(SignupActivity.this, "Password length must be more than least 6", Toast.LENGTH_SHORT).show();
                }
                else if(firstPassword.equals(CheckPassword))
                {
                    completeSignUp(); //completeSignUP실행
                }
                else {
                    Toast.makeText(SignupActivity.this, "Password is different!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void completeSignUp(){  //실행되면(OK버튼 누르면) 입력된 데이터들 mysql로 저장되도록 구현해야함.

        //mysql에 아이디 이메일 비밀번호를 차례로 저장한다. 그리고 다시 로그인창으로 가도록

        finish();   //스택에서 기존 페이지제거 즉, SignupActivity제거. 동기화 작업임.
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        Toast.makeText(getApplicationContext(), "ID has been created!", Toast.LENGTH_SHORT).show(); //로그인화면으로 왔을시 거기서 ID생성 토스트메시지 띄워줌
        startActivity(intent);
    }
}
