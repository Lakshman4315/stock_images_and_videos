package com.example.firstproject.VideoView;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;

public class fetchVideos extends AsyncTaskLoader<JSONArray> {

    private String query;

    public fetchVideos(@NonNull Context context, String query) {
        super(context);
        query = query;
    }

    @Nullable
    @Override
    public JSONArray loadInBackground() {
        return utils.getVideos(query);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
