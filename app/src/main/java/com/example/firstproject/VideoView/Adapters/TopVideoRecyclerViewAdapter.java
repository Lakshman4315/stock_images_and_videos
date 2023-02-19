package com.example.firstproject.VideoView.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.ImageView.ImageFragment;
import com.example.firstproject.MainActivity;
import com.example.firstproject.R;
import com.example.firstproject.VideoView.Model.TopVideoRecyclerViewModel;
import com.example.firstproject.VideoView.VideoFragment;

import java.util.ArrayList;

public class TopVideoRecyclerViewAdapter extends RecyclerView.Adapter<TopVideoRecyclerViewAdapter.ViewHolder>{

    private final ArrayList<TopVideoRecyclerViewModel> ButtonNameArray;
    private final Context context;

    public TopVideoRecyclerViewAdapter(Context context, ArrayList<TopVideoRecyclerViewModel> Button_Name_Array){
        this.context= context;
        this.ButtonNameArray= Button_Name_Array;
    }


    @NonNull
    @Override
    public TopVideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopVideoRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.video_item_top_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopVideoRecyclerViewAdapter.ViewHolder holder, int position) {
        TopVideoRecyclerViewModel currentButtonName = ButtonNameArray.get(position);
        holder.mButton.setText(currentButtonName.getButtonName());

    }

    @Override
    public int getItemCount() {
        return ButtonNameArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.videoButton);
            mButton.setOnClickListener(view -> {

//                VideoFragment videoFragment = new VideoFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("VALUE_OF_Q",mButton.getText().toString());
//                videoFragment.setArguments(bundle);
//
//                if(itemView.getContext() instanceof MainActivity){
//                    ((MainActivity)itemView.getContext()).getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.container , new VideoFragment()).commit();
//                }
//                ((VideoFragment) Fragment).searchVideos(mButton.getText().toString());

//                new VideoFragment().searchVideos(mButton.getText().toString());

                if(itemView.getContext() instanceof MainActivity){
                    MainActivity mainActivity = (MainActivity) itemView.getContext();
                    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                    VideoFragment videoFragment = (VideoFragment) fragmentManager
                            .findFragmentByTag("VideoFragment");
                    assert videoFragment != null;
                    videoFragment.searchVideos(mButton.getText().toString());
                }

            });
        }
    }

}
