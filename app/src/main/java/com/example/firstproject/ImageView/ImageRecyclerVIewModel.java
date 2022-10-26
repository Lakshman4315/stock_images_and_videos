package com.example.firstproject.ImageView;

public class ImageRecyclerVIewModel {

    private String ImageUrl;

    ImageRecyclerVIewModel(String ImageUrl){
        this.ImageUrl = ImageUrl;
    }

    String getImageUrl(){return this.ImageUrl;}
}
