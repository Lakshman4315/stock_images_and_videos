package com.example.firstproject.ImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstproject.Model.TopRecyclerViewModel;
import com.example.firstproject.R;
import com.example.firstproject.adapters.TopRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ImageFragment extends Fragment {

    private ArrayList<ImageRecyclerVIewModel> ImageUrlArray;
    private ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    private TopRecyclerViewAdapter topRecyclerViewAdapter;
    private ArrayList<TopRecyclerViewModel> myButtonNameArray;
    private Context context;

    private String valueOfq ;

    public ImageFragment(){}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("VALUE_OF_Q",valueOfq);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            valueOfq = savedInstanceState.getString("VALUE_OF_Q");
            Log.d("value",valueOfq);

        }else{
            valueOfq = "";
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

//        context= getContext();

        myButtonNameArray = new ArrayList<>();

        topRecyclerViewAdapter = new TopRecyclerViewAdapter(getActivity(), myButtonNameArray);

        RecyclerView myTopRecyclerView = view.findViewById(R.id.topRecyclerView);
        myTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        myTopRecyclerView.setAdapter(topRecyclerViewAdapter);
        myTopRecyclerView.setHasFixedSize(false);

        initializationTopRecyclerViewData();

        RecyclerView recyclerView = view.findViewById(R.id.imageRecyclerView);
        recyclerView.setHasFixedSize(false);
        ImageUrlArray = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(getContext(), ImageUrlArray);
        recyclerView.setAdapter(imageRecyclerViewAdapter);

//        new ApiCall().apiCall(getContext(), ImageUrlArray, valueOfq);
        ImageUrlArray.clear();
        apiCall(valueOfq,getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments()!=null){
            valueOfq= getArguments().getString("buttonString");
            Log.d("value",valueOfq);
        }else{
            valueOfq = "";
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializationTopRecyclerViewData() {
        String[] button_name_array = getResources().getStringArray(R.array.button_name_array);

        myButtonNameArray.clear();

        for (String s : button_name_array) {
            myButtonNameArray.add(new TopRecyclerViewModel(s));
        }

        topRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void apiCall(String s,Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String imageUrl = "https://pixabay.com/api/?key=30318797-739da6e504408acb0a125c7d3&q"+s;
        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, imageUrl,
                null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("hits");
                        ImageUrlArray.clear();
                        Log.d("json",Integer.toString(jsonArray.length()));
                        for (int i = 0; i<jsonArray.length(); i++){
                            String url = jsonArray.getJSONObject(i).getString("webformatURL");
                            ImageUrlArray.add(new ImageRecyclerVIewModel(url));
                        }
                        imageRecyclerViewAdapter.notifyDataSetChanged();
//                        jsonArray.remove();

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
                });
        requestQueue.add(jsonObjectRequest);
    }



}