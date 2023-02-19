package com.example.firstproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.firstproject.login.view.LoginActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int GALLERY_REQ_CODE = 1 ;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static  final  int IMAGEPICK_GALLERY_REQUEST = 300;
//    private static final int IMAGE_
    Button saveButton,skipButton;
    ShapeableImageView selectProfile;

    String ProfileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();

        selectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelector();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences profilePreferences = getSharedPreferences("USER_DATA_FILE",MODE_PRIVATE);
                SharedPreferences.Editor preferencesEditor = profilePreferences.edit();
                preferencesEditor.putString("PROFILE_URI", ProfileUri);
                preferencesEditor.apply();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void init(){
        saveButton = findViewById(R.id.save);
        skipButton = findViewById(R.id.skip);
        selectProfile = findViewById(R.id.profileSelect);
    }

    private void imageSelector() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery,GALLERY_REQ_CODE);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==GALLERY_REQ_CODE){
                //for gallery
                selectProfile.setBackground(getResources().getDrawable(R.drawable.image_background));
                if (data != null) {
                    ProfileUri = data.getDataString();
                    selectProfile.setImageURI(data.getData());
                    saveButton.setEnabled(true);
                }
            }
        }
    }
}