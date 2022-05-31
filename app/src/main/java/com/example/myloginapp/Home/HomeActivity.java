package com.example.myloginapp.Home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.myloginapp.GalleryInfo;
import com.example.myloginapp.Object;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myloginapp.LoginActivity;
import com.example.myloginapp.Map.MapFragment;
import com.example.myloginapp.Mypage.UserFragment;
import com.example.myloginapp.R;
import com.example.myloginapp.SearchController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();
    // 로그인 화면으로 넘어가고 싶다면, false
    static boolean isLogged = false;

    private static final int REQUEST_CAMERA = 100;
    private static final int SINGLE_PERMISSION=1004;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_ly, new HomeFragment())
                .commit();
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
                    launchMapActivity();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new MapFragment())
                            .commit();
                    return true;
                }
                case R.id.tab_heart: {
                    launchCameraActivity(); //OpenCV를 이용한 검색을 위함
                    return true;
                }
                case R.id.tab_user: {

                        if (isLogged) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.home_ly, new UserFragment())
                                    .commit();
                        } else {
                            myAlertBuilder.setTitle("로그인이 필요한 기능입니다.");
                            myAlertBuilder.setMessage("로그인을 해주세요.");
                            myAlertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });

                            myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
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

    private void launchMapActivity() {
    }

    private void launchCameraActivity() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},SINGLE_PERMISSION);
        }else {//권한이 있는 경우
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        switch(requestCode){

            case SINGLE_PERMISSION:
                if(grantResult.length>0 && grantResult[0]==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("앱 권한");
                    alertDialog.setMessage("해당 앱의 원활한 기능을 이용하시려면 애플리케이션 정보>권한 에서 변경해주세요.");
                    alertDialog.setPositiveButton("권한 설정",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    alertDialog.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CAMERA:
                Bundle extras = data.getExtras();
                Bitmap Bitimg = (Bitmap) extras.get("data");
                int count;
                for (GalleryInfo i:Object.art) {
                    count = SearchController.compareFeature(i.getImage(), Bitimg);
                    if(count!=0)
                        Log.d("art",i.getName());
                }
                break;

        }
    }
}

