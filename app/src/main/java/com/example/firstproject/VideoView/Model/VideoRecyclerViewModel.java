package com.example.firstproject.VideoView.Model;

public class VideoRecyclerViewModel {

    String videoUrl;

    public VideoRecyclerViewModel(String videoUrl){
        this.videoUrl= videoUrl;
    }

    public String getVideoUrl(){return videoUrl;}
}
