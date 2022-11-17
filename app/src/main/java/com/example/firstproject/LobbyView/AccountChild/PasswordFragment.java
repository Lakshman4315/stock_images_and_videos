package com.example.firstproject.LobbyView.AccountChild;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstproject.LobbyView.AccountFragment;
import com.example.firstproject.R;
import com.example.firstproject.login.Database.Dao;
import com.example.firstproject.login.Database.Model.model;
import com.example.firstproject.login.Database.UserDatabase;
import com.example.firstproject.login.view.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class PasswordFragment extends Fragment {

    public PasswordFragment() {
        // Required empty public constructor
    }

    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        MaterialToolbar passwordToolbar = view.findViewById(R.id.passwordToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        appCompatActivity.setSupportActionBar(passwordToolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        passwordToolbar.setNavigationOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AccountFragment())
                        .commit()
        );

        SharedPreferences userPreferences =requireActivity().getSharedPreferences("USER_DATA_FILE", Context.MODE_PRIVATE);
        String email = userPreferences.getString("USER_EMAIL","");

        //Dao Object
        Dao dao = UserDatabase.getDatabase(getContext()).dao();

        EditText oldPassEditText = view.findViewById(R.id.oldPasswordEdit);

        EditText newPassEditText = view.findViewById(R.id.newPasswordEdit);

        Button passEditButton = view.findViewById(R.id.passwordEditButton);
        passEditButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                if(dao.login(email,oldPassEditText.getText().toString())){
                    if(newPassEditText.getText().toString().length()>=8){
                        dao.updateUserPass(email,newPassEditText.getText().toString());
                        Toast.makeText(getContext(), "Phone Number changes to "+newPassEditText.getText(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"Password Length Must Be Greater Than 8",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Enter Correct Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}