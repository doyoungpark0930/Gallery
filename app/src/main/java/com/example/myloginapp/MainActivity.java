package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myloginapp.Home.HomeActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    NotificationCompat.Builder builder;
    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    public static String IP_ADDRESS = "35.174.139.168";
    static boolean isUpdate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("한눈의 갤러리");
        setContentView(R.layout.activity_main);
        // 임시로 정렬을 확인하기 위해 사용함
        // 이미지 로더 부분
        Button getStarted = (Button) findViewById(R.id.getStarted);

        LoginLoader LoginLoader = new LoginLoader();
        LoginLoader.execute("http://" +IP_ADDRESS + "/select.php", "");

        GalleryLoader Loader = new GalleryLoader();
        Loader.execute("http://" + IP_ADDRESS + "/ArtSelect.php", "");

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder = null;
//
//                Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.drawable.gallery);
//
//                PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
//                        new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//                builder = new NotificationCompat.Builder(MainActivity.this, "default")
//                                .setSmallIcon(R.drawable.popup_img)
//                                .setContentTitle("알림 제목")
//                                .setContentText("알림 내용!!")
//                                .setDefaults(Notification.DEFAULT_VIBRATE)
//                                .setLargeIcon(mLargeIconForNoti)
//                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                                .setDefaults(Notification.DEFAULT_VIBRATE)
//                                .setAutoCancel(true)
//                                .setContentIntent(mPendingIntent);
//
//                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
//                }
//                manager.notify(1, builder.build());

                navigateToLoginActivity(); //getStarted버튼 클릭 시 LoginActivity로 이동.
                //AndroidManifest에 <activity android:name=".LoginActivity"></activity> 추가해줘야함
                //나머지 다른 activity도 추가할 때 androidManifest에 추가하기, 안하면 클릭시 앱 종료됨
            }
        });
        
        // 추가된 라인
//        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();
    }

    void Notification(){
        NotificationManager manager;
        NotificationCompat.Builder builder;
        builder = null;

        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.drawable.gallery);

        PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(MainActivity.this, "default")
                .setSmallIcon(R.drawable.popup_img)
                .setContentTitle("알림 제목")
                .setContentText("알림 내용!!")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setLargeIcon(mLargeIconForNoti)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setContentIntent(mPendingIntent);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        manager.notify(1, builder.build());

        navigateToLoginActivity(); //getStarted버튼 클릭 시 LoginActivity로 이동.
        //AndroidManifest에 <activity android:name=".LoginActivity"></activity> 추가해줘야함
        //나머지 다른 activity도 추가할 때 androidManifest에 추가하기, 안하면 클릭시 앱 종료됨

    }
    void navigateToLoginActivity(){ //LoginActivity로 가는 함수
        finish();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}