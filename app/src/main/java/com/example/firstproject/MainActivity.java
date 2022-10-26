package com.example.firstproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.ImageView.ImageFragment;
import com.example.firstproject.Model.TopRecyclerViewModel;
import com.example.firstproject.adapters.TopRecyclerViewAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar myToolBar;

    private BottomNavigationView myBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolBar = findViewById(R.id.toolBar);
        setSupportActionBar(myToolBar);



        myBottomNavigationBar = findViewById(R.id.bottomNavigation);
        bottomNavigationListener();
    }

    private void bottomNavigationListener() {
        myBottomNavigationBar.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.images){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ImageFragment()).commit();
                return true;
            }else{
                return true;
            }
        });
    }




}
