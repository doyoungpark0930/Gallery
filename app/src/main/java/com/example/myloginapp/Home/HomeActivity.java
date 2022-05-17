package com.example.myloginapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myloginapp.Heart.HeartFragment;
import com.example.myloginapp.LoginActivity;
import com.example.myloginapp.MainActivity;
import com.example.myloginapp.Map.MapFragment;
import com.example.myloginapp.R;
import com.example.myloginapp.Mypage.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();
    // 로그인 화면으로 넘어가고 싶다면, false
    boolean isLogged = true;

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "한눈의 갤러리에 입장하셨습니다.");

        init();
        SettingListener();

    }

    private void init(){
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(HomeActivity.this);

            switch (menuItem.getItemId()) {
                case R.id.tab_home: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new HomeFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_map: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new MapFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_heart: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new HeartFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_user: {
                    if(isLogged){
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_ly, new UserFragment())
                                .commit();
                    }else{
                        myAlertBuilder.setTitle("로그인이 필요한 기능입니다.");
                        myAlertBuilder.setMessage("로그인을 해주세요.");
                        myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });

                        myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        });

                        myAlertBuilder.show();
                    }
                    return true;
                }
            }

            return false;
        }
    }

}

