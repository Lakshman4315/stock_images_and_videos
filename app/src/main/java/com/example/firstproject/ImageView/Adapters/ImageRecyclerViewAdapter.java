package com.example.firstproject.ImageView.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firstproject.ImageView.Model.ImageRecyclerVIewModel;
import com.example.firstproject.R;

import java.util.ArrayList;
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<ImageRecyclerVIewModel> ImageUrlArray;

    private Context context;

    public ImageRecyclerViewAdapter(Context context, ArrayList<ImageRecyclerVIewModel> ImageUrlArray){
        this.context = context;
        this.ImageUrlArray = ImageUrlArray;
    }

    @NonNull
    @Override
    public ImageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ImageRecyclerViewAdapter.ViewHolder holder, int position) {
        ImageRecyclerVIewModel currentData = ImageUrlArray.get(position);
//        holder.bind(currentData);

        Glide.with(context).load(currentData.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ImageUrlArray.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }



//        public void bind(ImageRecyclerVIewModel currentData) {
//
//            Glide.with(context).load(currentData).into(imageView);
//        }
    }
}
