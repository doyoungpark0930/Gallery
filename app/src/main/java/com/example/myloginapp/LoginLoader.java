package com.example.myloginapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginLoader extends AsyncTask<String, Void, String> {

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

            showResult(sb.toString());
            bufferedReader.close();

            return sb.toString().trim();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private void showResult(String mJsonString) {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("UserTable");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String no = item.getString("num");
                String id = item.getString("id");
                String passwd = item.getString("passwd");
                String email = item.getString("email");

                Object.userlist.add(new UserInfo(Integer.parseInt(no), id, passwd, email));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}