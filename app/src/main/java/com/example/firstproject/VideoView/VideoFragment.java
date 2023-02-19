package com.example.firstproject.VideoView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;
import com.example.firstproject.VideoView.Adapters.TopVideoRecyclerViewAdapter;
import com.example.firstproject.VideoView.Adapters.VideoRecyclerViewAdapter;
import com.example.firstproject.VideoView.Model.TopVideoRecyclerViewModel;
import com.example.firstproject.VideoView.Model.VideoRecyclerViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<JSONArray> {

    private TopVideoRecyclerViewAdapter topVideoRecyclerViewAdapter;
    private ArrayList<TopVideoRecyclerViewModel> myButtonNameArray;

    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private ArrayList<VideoRecyclerViewModel> VideoUrlArray;

    String valueOfq;


    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            valueOfq = bundle.getString("VALUE_OF_Q","");
        }else{
            valueOfq = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //for landscape mode
        int orientation = getResources().getConfiguration().orientation;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        myButtonNameArray = new ArrayList<>();

        topVideoRecyclerViewAdapter = new TopVideoRecyclerViewAdapter(getActivity(), myButtonNameArray);

        RecyclerView myTopRecyclerView = view.findViewById(R.id.topRecyclerView);
        myTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));

        int gridColumnCount = 1;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            myTopRecyclerView.setVisibility(View.GONE);
            gridColumnCount = 2;
        }

        myTopRecyclerView.setAdapter(topVideoRecyclerViewAdapter);
        myTopRecyclerView.setHasFixedSize(false);

        initializationTopVideoRecyclerViewData();

        myTopRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.videoRecyclerView);
        recyclerView.setHasFixedSize(false);
        VideoUrlArray = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),gridColumnCount));
        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), VideoUrlArray);
        recyclerView.setAdapter(videoRecyclerViewAdapter);

//        apiCall(valueOfq,getContext());

        searchVideos("");

        // When using AsyncTaskLoader
        if(requireActivity().getSupportLoaderManager().getLoader(0)!=null){
            requireActivity().getSupportLoaderManager().initLoader(0,null,this);
        }

        // For remove any Item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.
                        SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        VideoUrlArray.remove(viewHolder.getAbsoluteAdapterPosition());
                        videoRecyclerViewAdapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);

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

    public void searchVideos(String query){
        @SuppressLint("ServiceCast") ConnectivityManager connectivityManager = (ConnectivityManager)
                requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }


        if (networkInfo != null && networkInfo.isConnected()){
            Bundle queryBundle = new Bundle();
            queryBundle.putString("VALUE_OF_Q", query);
            requireActivity().getSupportLoaderManager().restartLoader(0,queryBundle,this);

        }else{
            Toast.makeText(requireContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();
        }


    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int id, @Nullable Bundle args) {
        if(args!=null){
            valueOfq=args.getString("VALUE_OF_Q");
        }
        return new fetchVideos(requireContext(),valueOfq);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray data) {
        try{
            VideoUrlArray.clear();
            videoRecyclerViewAdapter.notifyDataSetChanged();
            for (int i = 0; i<data.length(); i++){
                String url = data.getJSONObject(i).getJSONObject("videos").getJSONObject("large").getString("url");
                VideoUrlArray.add(new VideoRecyclerViewModel(url));
                Log.d("url",url);
            }
            videoRecyclerViewAdapter.notifyDataSetChanged();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {
    }

}