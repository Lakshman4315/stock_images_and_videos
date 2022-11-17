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


public class EmailFragment extends Fragment {



    public EmailFragment() {
        // Required empty public constructor
    }

    public static EmailFragment newInstance(String param1, String param2) {
        EmailFragment fragment = new EmailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email, container, false);

        MaterialToolbar emailToolbar = view.findViewById(R.id.emailToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        appCompatActivity.setSupportActionBar(emailToolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        emailToolbar.setNavigationOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AccountFragment())
                        .commit()
        );

        SharedPreferences userPreferences =requireActivity().getSharedPreferences("USER_DATA_FILE", Context.MODE_PRIVATE);
        String email = userPreferences.getString("USER_EMAIL","");

        //Dao Object
        Dao dao = UserDatabase.getDatabase(getContext()).dao();

        EditText emailEditText = view.findViewById(R.id.emailEdit);
        emailEditText.setText(dao.getUserData(email).getEmail());

        Button editButton = view.findViewById(R.id.emailEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {
                dao.updateUserEmail(email,emailEditText.getText().toString());
                Toast.makeText(getContext(), "Email changes to"+emailEditText.getText(), Toast.LENGTH_SHORT).show();
                userPreferences.edit().putString("USER_EMAIL",emailEditText.getText().toString());
            }
        });

        return view;
    }
}