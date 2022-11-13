package com.example.firstproject.VideoView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstproject.R;
import com.example.firstproject.VideoView.Adapters.TopVideoRecyclerViewAdapter;
import com.example.firstproject.VideoView.Adapters.VideoRecyclerViewAdapter;
import com.example.firstproject.VideoView.Model.TopVideoRecyclerViewModel;
import com.example.firstproject.VideoView.Model.VideoRecyclerViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private TopVideoRecyclerViewAdapter topVideoRecyclerViewAdapter;
    private ArrayList<TopVideoRecyclerViewModel> myButtonNameArray;

    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private ArrayList<VideoRecyclerViewModel> VideoUrlArray;

    private String valueOfq;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        myButtonNameArray = new ArrayList<>();

        topVideoRecyclerViewAdapter = new TopVideoRecyclerViewAdapter(getActivity(), myButtonNameArray);

        RecyclerView myTopRecyclerView = view.findViewById(R.id.topRecyclerView);
        myTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        myTopRecyclerView.setAdapter(topVideoRecyclerViewAdapter);
        myTopRecyclerView.setHasFixedSize(false);

        initializationTopVideoRecyclerViewData();

        RecyclerView recyclerView = view.findViewById(R.id.videoRecyclerView);
        recyclerView.setHasFixedSize(false);
        VideoUrlArray = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), VideoUrlArray);
        recyclerView.setAdapter(videoRecyclerViewAdapter);

//        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(@NonNull View view) {
//                StyledPlayerControlView styledPlayerControlView = (StyledPlayerControlView) view.findViewById(R.id.exoPlayerView);
////                if(styledPlayerControlView != null){
////                    styledPlayerControlView.
////                }
////                ExoPlayer exoPlayer = (ExoPlayer) view.findViewById(R.)
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(@NonNull View view) {
//
//            }
//        });


        valueOfq = "mountains";

        apiCall(valueOfq,getContext());

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializationTopVideoRecyclerViewData() {
        String[] button_name_array = getResources().getStringArray(R.array.button_name_array);

        myButtonNameArray.clear();

        for (String s : button_name_array) {
            myButtonNameArray.add(new TopVideoRecyclerViewModel(s));
        }

        topVideoRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void apiCall(String s, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String videoUrl = "https://pixabay.com/api/videos/?key=30318797-739da6e504408acb0a125c7d3&q"+s+"&per_page=20";
        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, videoUrl,
                null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("hits");
                        VideoUrlArray.clear();
                        for (int i = 0; i<jsonArray.length(); i++){
                            String url = jsonArray.getJSONObject(i).getJSONObject("videos").getJSONObject("large").getString("url");
                            VideoUrlArray.add(new VideoRecyclerViewModel(url));
                            Log.d("url",url);
                        }
                        videoRecyclerViewAdapter.notifyDataSetChanged();
//                        jsonArray.remove();

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
        });
        requestQueue.add(jsonObjectRequest);
    }
}