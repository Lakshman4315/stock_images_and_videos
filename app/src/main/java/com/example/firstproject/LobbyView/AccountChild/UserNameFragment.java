package com.example.firstproject.LobbyView.AccountChild;

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
import com.example.firstproject.LobbyView.LobbyFragment;
import com.example.firstproject.R;
import com.example.firstproject.login.Database.Dao;
import com.example.firstproject.login.Database.UserDatabase;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;


public class UserNameFragment extends Fragment {


    public UserNameFragment() {
        // Required empty public constructor
    }


    public static UserNameFragment newInstance(String param1, String param2) {
        UserNameFragment fragment = new UserNameFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_name, container, false);

        MaterialToolbar usernameToolbar = view.findViewById(R.id.usernameToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        appCompatActivity.setSupportActionBar(usernameToolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        usernameToolbar.setNavigationOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AccountFragment())
                        .commit()
        );

        SharedPreferences userPreferences =requireActivity().getSharedPreferences("USER_DATA_FILE", Context.MODE_PRIVATE);
        String email = userPreferences.getString("USER_EMAIL","");

        //Dao Object
        Dao dao = UserDatabase.getDatabase(getContext()).dao();

        EditText userNameEditText = view.findViewById(R.id.usernameEdit);
        userNameEditText.setText(dao.getUserData(email).getUsername());

        Button editButton = view.findViewById(R.id.userNameEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.updateUser(email,userNameEditText.getText().toString());
                Toast.makeText(getContext(), "Username changes to"+userNameEditText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}