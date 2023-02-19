package com.example.firstproject.LobbyView;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstproject.R;
import com.example.firstproject.login.Database.Dao;
import com.example.firstproject.login.Database.UserDatabase;
import com.example.firstproject.login.view.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;


public class LobbyFragment extends Fragment {

    private ImageView ProfileImage;
    private static final int GALLERY_REQ_CODE = 300;

    private Dao dao;

    private String email;

    public LobbyFragment() {
        // Required empty public constructor
    }


    public static LobbyFragment newInstance(String param1, String param2) {
        LobbyFragment fragment = new LobbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lobby, container, false);

        ProfileImage = view.findViewById(R.id.profileDP);

        dao = UserDatabase.getDatabase(getContext()).dao();

        SharedPreferences profileSharedPreferences = requireActivity().getSharedPreferences("USER_DATA_FILE",0);
        email = profileSharedPreferences.getString("USER_EMAIL","");
        String profileUri = profileSharedPreferences.getString("PROFILE_URI","");
        if(!profileUri.equals("")){
            ProfileImage.setImageURI(Uri.parse(profileUri));
        }else{
            ProfileImage.setImageResource(R.drawable.component2);
        }

        //FAB Functionality
        FloatingActionButton fab = view.findViewById(R.id.editProfile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,GALLERY_REQ_CODE);
            }
        });



        //Account TextView Functionality
        MaterialTextView AccountTextView = view.findViewById(R.id.account);
        AccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AccountFragment())
                        .commit();
            }
        });


        //Logout Button Functionality
        MaterialButton logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            SharedPreferences sharedPreferences = requireActivity()
                    .getSharedPreferences("com.example.android.firstProject",Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("loggedIn",false).apply();
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                if(data!=null){
                    ProfileImage.setImageURI(data.getData());

                    dao.updateUserProfile(email,data.getData().toString());
                }
            }
        }
    }

}