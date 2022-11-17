package com.example.firstproject.LobbyView.AccountChild;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.firstproject.login.Database.UserDatabase;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class PhoneFragment extends Fragment {

    public PhoneFragment() {
        // Required empty public constructor
    }

    public static PhoneFragment newInstance(String param1, String param2) {
        PhoneFragment fragment = new PhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_phone, container, false);

        MaterialToolbar phoneToolbar = view.findViewById(R.id.phoneToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        appCompatActivity.setSupportActionBar(phoneToolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        phoneToolbar.setNavigationOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AccountFragment())
                        .commit()
        );

        SharedPreferences userPreferences =requireActivity().getSharedPreferences("USER_DATA_FILE", Context.MODE_PRIVATE);
        String email = userPreferences.getString("USER_EMAIL","");

        //Dao Object
        Dao dao = UserDatabase.getDatabase(getContext()).dao();

        EditText phoneEditText = view.findViewById(R.id.phoneEdit);
        phoneEditText.setText(dao.getUserData(email).getPhone_no());

        Button phoneEditButton = view.findViewById(R.id.phoneEditButton);
        phoneEditButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {
                dao.updateUserPhone(email,phoneEditText.getText().toString());
                Toast.makeText(getContext(), "Phone Number changes to "+phoneEditText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}