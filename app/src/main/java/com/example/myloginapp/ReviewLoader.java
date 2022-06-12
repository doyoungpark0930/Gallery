package com.example.myloginapp;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myloginapp.Description.DesReviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.Executor;


public class ReviewLoader extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String serverURL = (String) params[0];

        try {
            URL loginurl = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) loginurl.openConnection();

            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

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
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            showReview(sb.toString());
            bufferedReader.close();

            return sb.toString().trim();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /*
    @Override
    protected void onProgressUpdate(Long... values) {
        desReviewAdapter=new DesReviewAdapter(Object.review);
        recyclerView.setAdapter(desReviewAdapter);
    }
    */
    private void showReview(String mJsonString) {

        try {
            Object.review.clear();
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("ReviewTable");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String usernum = item.getString("usernum");
                String artnum = item.getString("artnum");
                String review = item.getString("mention");
                int star = item.getInt("star");
                Log.e("tag",review);
                Object.review.add(new DesReviewInfo(Integer.parseInt(usernum), Integer.parseInt(artnum), review, star));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
