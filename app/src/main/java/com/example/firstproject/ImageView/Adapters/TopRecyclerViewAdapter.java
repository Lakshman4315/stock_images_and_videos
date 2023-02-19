package com.example.firstproject.ImageView.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.ImageView.ImageFragment;
import com.example.firstproject.ImageView.Model.TopRecyclerViewModel;
import com.example.firstproject.MainActivity;
import com.example.firstproject.R;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<TopRecyclerViewModel> ButtonNameArray;
    private final Context context;

    public TopRecyclerViewAdapter(Context context, ArrayList<TopRecyclerViewModel> Button_Name_Array){
        this.context= context;
        this.ButtonNameArray= Button_Name_Array;
    }


    @NonNull
    @Override
    public TopRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_top_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecyclerViewAdapter.ViewHolder holder, int position) {
        TopRecyclerViewModel currentButtonName = ButtonNameArray.get(position);
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
            mButton = itemView.findViewById(R.id.button);
            mButton.setOnClickListener(view -> {

                ImageFragment imageFragment = new ImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("buttonString",mButton.getText().toString());
                imageFragment.setArguments(bundle);

//                if(itemView.getContext() instanceof MainActivity){
//                    ((MainActivity)itemView.getContext()).getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.container , new ImageFragment()).commit();
//                }


            });
        }
    }
}



