package com.example.firstproject;

import android.annotation.SuppressLint;
import android.app.SearchManager;
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

    private MaterialToolbar myToolBar;

    private BottomNavigationView myBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolBar = findViewById(R.id.toolBar);
        setSupportActionBar(myToolBar);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

        myBottomNavigationBar = findViewById(R.id.bottomNavigation);
        myBottomNavigationBar.getMenu().findItem(R.id.images).setChecked(true);
        bottomNavigationListener();

    }

    private void doMySearch(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("VALUE_OF_Q",query);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.search_icon){
            onSearchRequested();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigationListener() {

        myBottomNavigationBar.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.videos:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoFragment()).commit();
                    return true;
                case R.id.lobby:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LobbyFragment()).commit();
                    return true;
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ImageFragment()).commit();
                    return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
