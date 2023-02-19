package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.firstproject.login.view.LoginActivity;

public class StartingActivity extends AppCompatActivity {
    
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                SharedPreferences sharedPreferences = getSharedPreferences("com.example.android.firstProject",MODE_PRIVATE);
////                Boolean hasLoggedIn = sharedPreferences.getBoolean("loggedIn", false);
//            }
//        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.android.firstProject",0);
                Boolean hasLoggedIn = sharedPreferences.getBoolean("loggedIn",false);
                if(hasLoggedIn){
                    Intent  intent = new Intent(StartingActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(StartingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}