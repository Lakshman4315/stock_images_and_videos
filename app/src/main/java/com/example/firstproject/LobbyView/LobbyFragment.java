package com.example.firstproject.LobbyView;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.MainActivity;
import com.example.firstproject.R;
import com.google.android.material.textview.MaterialTextView;


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

        MaterialTextView accountTextView = view.findViewById(R.id.account);
        accountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AccountActivity.class));
            }
        });

        MaterialTextView userProfileTextView = view.findViewById(R.id.YourDP);
        MaterialTextView imageTextView = view.findViewById(R.id.yourImage);
        MaterialTextView videoTextView = view.findViewById(R.id.YourVideo);

        return view;
    }
}