package com.example.firstproject.VideoView.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;
import com.example.firstproject.VideoView.Model.VideoRecyclerViewModel;

import java.util.ArrayList;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>{

    private ArrayList<VideoRecyclerViewModel> mVideoUrl;
    private final Context mContext;

    public VideoRecyclerViewAdapter(Context context, ArrayList<VideoRecyclerViewModel> videoUrl){
        this.mVideoUrl = videoUrl;
        this.mContext = context;
    }

    @NonNull
    @Override
    public VideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.video_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.ViewHolder holder, int position) {
        VideoRecyclerViewModel currentUrl = mVideoUrl.get(position);
        Uri uri = Uri.parse(currentUrl.getVideoUrl());
        MediaItem mediaItem = MediaItem.fromUri(uri);
        holder.exoPlayer.setMediaItem(mediaItem);
        holder.exoPlayer.prepare();
    }

    @Override
    public int getItemCount() {
        return mVideoUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ExoPlayer exoPlayer;
        PlayerView exoPlayerView;
        public ViewHolder( View itemView) {
            super(itemView);
            exoPlayerView = itemView.findViewById(R.id.exoPlayerView);
            exoPlayer = new ExoPlayer.Builder(mContext).build();
            exoPlayerView.setPlayer(exoPlayer);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.exoPlayer.pause();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if(holder.exoPlayerView.isControllerFullyVisible()){
//            if(holder.exoPlayerView.)
            holder.exoPlayer.play();
        }else{
            holder.exoPlayer.pause();
        }
    }
}

