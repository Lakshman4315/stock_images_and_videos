package com.example.firstproject.LobbyView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.MainActivity;
import com.example.firstproject.R;
import com.example.firstproject.login.view.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;


public class LobbyFragment extends Fragment {


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
}