package com.example.firstproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.ImageView.ImageFragment;
import com.example.firstproject.LobbyView.LobbyFragment;
import com.example.firstproject.VideoView.VideoFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//    private MaterialToolbar myToolBar;

    private BottomNavigationView myBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myToolBar = findViewById(R.id.toolBar);
//        setSupportActionBar(myToolBar);

        myBottomNavigationBar = findViewById(R.id.bottomNavigation);
        myBottomNavigationBar.setSelected(true);
        bottomNavigationListener();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.search_icon){
//
//        }else{
//            startActivity(new Intent(this,HelpActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigationListener() {

        myBottomNavigationBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.images:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ImageFragment()).commit();
                    return true;
                case R.id.videos:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoFragment()).commit();
                    return true;
                case R.id.lobby:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LobbyFragment()).commit();
                    return true;
                default:
                    return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
