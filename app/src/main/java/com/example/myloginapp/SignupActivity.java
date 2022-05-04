package com.example.myloginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity"; //로그 찍기위한 TAG변수. 로그 안 찍어볼거면 무시해도
    private static String IP_ADDRESS = "113.198.138.221"; //현재 나의 ip번호 -> 서버로 변경할 예정임.

    private String id;
    private String passwd;
    private String email;
    TextView sign;
    EditText Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MaterialButton signupCompletebtn = (MaterialButton) findViewById(R.id.signupCompletebtn);

        EditText Password = (EditText) findViewById(R.id.password); //입력된 passwd
        EditText ConfigPassword = (EditText) findViewById(R.id.configPassword); //입력된 확인 passwd
        EditText Email = (EditText) findViewById(R.id.EmailAddress); //입력된 email
        Id = (EditText) findViewById(R.id.username); //입력된 id
        sign = (TextView) findViewById(R.id.signup);
        signupCompletebtn.setOnClickListener(new View.OnClickListener() { //밑에 OK버튼 클릭하면
            @Override
            public void onClick(View v) {
                String firstPassword = Password.getText().toString();
                String CheckPassword = ConfigPassword.getText().toString();
                String CheckEmail = Email.getText().toString();
                if (firstPassword.length() < 6) //패스워드 글자수가 6이하라면
                {
                    Toast.makeText(SignupActivity.this, "Password length must be more than least 6", Toast.LENGTH_SHORT).show();
                } else if (firstPassword.equals(CheckPassword)) {
                    if (CheckEmail.contains("@")) {
                        passwd = CheckPassword;
                        email = CheckEmail;
                        id = Id.getText().toString();

                        completeSignUp();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Password is different!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void completeSignUp() {  //실행되면(OK버튼 누르면) 입력된 데이터들 mysql로 저장되도록 구현해야함.

        //mysql에 아이디 이메일 비밀번호를 차례로 저장한다. 그리고 다시 로그인창으로 가도록
        DBLoader task = new DBLoader();
        task.execute("http://" + IP_ADDRESS + "/signup.php", id, passwd, email);
        finish();   //스택에서 기존 페이지제거 즉, SignupActivity제거. 동기화 작업임.
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        Toast.makeText(getApplicationContext(), "ID has been created!", Toast.LENGTH_SHORT).show(); //로그인화면으로 왔을시 거기서 ID생성 토스트메시지 띄워줌
        startActivity(intent);

    }

    class DBLoader extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SignupActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[1];
            String passwd = (String) params[2];
            String email = (String) params[3];
            String serverURL = (String) params[0];
            String postParameters = "id=" + id + "&passwd=" + passwd + "&email=" + email;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                return new String("Error: " + e.getMessage());
            }
        }

    }
}
