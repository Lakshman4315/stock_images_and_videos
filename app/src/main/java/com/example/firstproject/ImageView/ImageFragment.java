package com.example.firstproject.ImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstproject.ImageView.Adapters.ImageRecyclerViewAdapter;
import com.example.firstproject.ImageView.Adapters.TopRecyclerViewAdapter;
import com.example.firstproject.ImageView.Model.ImageRecyclerVIewModel;
import com.example.firstproject.ImageView.Model.TopRecyclerViewModel;
import com.example.firstproject.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ImageFragment extends Fragment {

    private ArrayList<ImageRecyclerVIewModel> ImageUrlArray;
    private ImageRecyclerViewAdapter imageRecyclerViewAdapter;
    private TopRecyclerViewAdapter topRecyclerViewAdapter;
    private ArrayList<TopRecyclerViewModel> myButtonNameArray;

    String valueOfq;

    public ImageFragment(){
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            valueOfq  = getArguments().getString("buttonString");
        }else{
            valueOfq = "";
        }
        Log.d("data",valueOfq);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int orientation = getResources().getConfiguration().orientation;
        View view = inflater.inflate(R.layout.fragment_image, container, false);


        myButtonNameArray = new ArrayList<>();

        topRecyclerViewAdapter = new TopRecyclerViewAdapter(getActivity(), myButtonNameArray);

        RecyclerView myTopRecyclerView = view.findViewById(R.id.topRecyclerView);
        myTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));

        int gridColumnCount=1;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            myTopRecyclerView.setVisibility(View.GONE);
            gridColumnCount = 2;
        }

        myTopRecyclerView.setAdapter(topRecyclerViewAdapter);
        myTopRecyclerView.setHasFixedSize(false);

        initializationTopRecyclerViewData();

        RecyclerView recyclerView = view.findViewById(R.id.imageRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),gridColumnCount));
        ImageUrlArray = new ArrayList<>();
        imageRecyclerViewAdapter = new ImageRecyclerViewAdapter(getContext(), ImageUrlArray);
        recyclerView.setAdapter(imageRecyclerViewAdapter);

        apiCall(valueOfq,getContext());

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.
                        SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        ImageUrlArray.remove(viewHolder.getAbsoluteAdapterPosition());
                        imageRecyclerViewAdapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);

        return view;
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
        String imageUrl = "https://pixabay.com/api/?key=30318797-739da6e504408acb0a125c7d3&q="+s+"&per_page=100";
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
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
                });
        requestQueue.add(jsonObjectRequest);
    }
}