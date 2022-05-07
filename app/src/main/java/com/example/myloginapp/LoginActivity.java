package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    static String id;
    static String passwd;
    public static String IP_ADDRESS = "113.198.138.221"; //현재 나의 ip번호 -> 서버로 변경할 예정임.
    TextView forgat;
    public UserInfo CurUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        forgat=(TextView)findViewById(R.id.forgotpass);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton signupbtn = (MaterialButton) findViewById(R.id.signupbtn);
        LoginLoader loginLoader = new LoginLoader();
        loginLoader.execute("http://" + IP_ADDRESS + "/select.php",id, passwd);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=username.getText().toString();
                passwd=password.getText().toString();

                boolean login=false;
                for(UserInfo user : LoginLoader.users){
                    if(user.getId().equals(id))
                        if(user.getPasswd().equals(passwd)) {
                            CurUser = user;
                            login=true;
                        }
                }
                if(login) { //로그인이 된다면 실행됨
                    forgat.setText(CurUser.getUserNum() + " " + CurUser.getId() + " " + CurUser.getEmail() + " ");
                    navigateToSearchingGallery();
                }else{  //로그인이 실패됐다면
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show(); //로그인화면으로 왔을시 거기서 ID생성 토스트메시지 띄워줌
                }
            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUpActivity();
            }
        });

    }

    void navigateToSearchingGallery()
    {
        finish();
        Intent intent = new Intent(LoginActivity.this, SearchingGallery.class);
        startActivity(intent);
    }
    void navigateToSignUpActivity(){
        finish();
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
