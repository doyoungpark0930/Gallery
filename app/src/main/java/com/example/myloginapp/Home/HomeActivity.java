package com.example.myloginapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.myloginapp.Heart.HeartFragment;
import com.example.myloginapp.Map.MapFragment;
import com.example.myloginapp.R;
import com.example.myloginapp.Mypage.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "한눈의 갤러리에 입장하셨습니다.");

        init();
        SettingListener();

        bottomNavigationView.setSelectedItemId(R.id.home);
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
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new UserFragment())
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }

}

