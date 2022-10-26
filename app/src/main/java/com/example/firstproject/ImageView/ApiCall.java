package com.example.firstproject.ImageView;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstproject.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ApiCall {


    public void apiCall(Context context, ArrayList<ImageRecyclerVIewModel> imageUrlArray, String valueOfq){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String imageUrl = "https://pixabay.com/api/?key="+context.getString(R.string.apiKey)+"&q"+valueOfq;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, imageUrl,
                null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("hits");
                        imageUrlArray.clear();
                        for (int i = 0; i<jsonArray.length(); i++){
                            String url = jsonArray.getJSONObject(i).getString("imageUrl");
                            imageUrlArray.add(new ImageRecyclerVIewModel(url));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
                });
        requestQueue.add(jsonObjectRequest);
    }


}
