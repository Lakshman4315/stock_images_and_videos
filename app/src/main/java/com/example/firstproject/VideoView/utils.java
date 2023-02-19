package com.example.firstproject.VideoView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstproject.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class utils {

    private static final String VIDEO_BASE_URL = "https://pixabay.com/api/videos/?key=30318797-739da6e504408acb0a125c7d3&";

    static JSONArray getVideos(String query){

//        AtomicReference<JSONArray> jsonArray = null;
//        RequestQueue requestQueue = Volley.newRequestQueue(new VideoFragment().requireContext());
//
//        String videoUrl = "https://pixabay.com/api/videos/?key=30318797-739da6e504408acb0a125c7d3&q="+query+"&per_page=20";
//        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, videoUrl,
//                null,
//                response -> {
//                    try{
//                        assert false;
//                        jsonArray.set(response.getJSONArray("hits"));
//
//                    } catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                }, error -> {
//        });
//        requestQueue.add(jsonObjectRequest);
//
//        assert false;
//        return jsonArray.get();


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        JSONArray jsonArray = null;


        try{
            Uri builtURI = Uri.parse(VIDEO_BASE_URL).buildUpon()
                    .appendQueryParameter("q=",query)
                    .build();

            try {
                URL requestURL = new URL(builtURI.toString());

                urlConnection = (HttpURLConnection) requestURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine())!=null){
                    builder.append(line);

                    if(builder.length()==0) {
                        return null;
                    }
                }
                try {
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    jsonArray = jsonObject.getJSONArray("hits");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }
}
