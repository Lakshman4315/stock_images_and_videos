package com.example.firstproject.LobbyView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstproject.LobbyView.AccountChild.EmailFragment;
import com.example.firstproject.LobbyView.AccountChild.PasswordFragment;
import com.example.firstproject.LobbyView.AccountChild.PhoneFragment;
import com.example.firstproject.LobbyView.AccountChild.UserNameFragment;
import com.example.firstproject.R;
import com.example.firstproject.login.Database.Dao;
import com.example.firstproject.login.Database.UserDatabase;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        MaterialToolbar accountToolbar = view.findViewById(R.id.accountToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        assert appCompatActivity != null;
        appCompatActivity.setSupportActionBar(accountToolbar);
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        accountToolbar.setNavigationOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new LobbyFragment())
                    .commit()
        );



        SharedPreferences userPreferences =requireActivity().getSharedPreferences("USER_DATA_FILE",Context.MODE_PRIVATE);
        String email = userPreferences.getString("USER_EMAIL","");

        //fetching user data from database
        Dao dao = UserDatabase.getDatabase(getContext()).dao();
        String name = dao.getUserData(email).getUsername();
        String phoneNo = dao.getUserData(email).getPhone_no();
        String pass = dao.getUserData(email).getPassword();

        MaterialTextView userNameTextView = view.findViewById(R.id.USERNAME);
        userNameTextView.setText(name);
        userNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new UserNameFragment())
                        .commit();
            }
        });

        MaterialTextView emailTextView = view.findViewById(R.id.EMAIL);
        emailTextView.setText(email);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new EmailFragment())
                        .commit();
            }
        });

        MaterialTextView phoneTextView = view.findViewById(R.id.PHONE_NUMBER);
        phoneTextView.setText(phoneNo);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new PhoneFragment())
                        .commit();
            }
        });

        MaterialTextView passwordTextView = view.findViewById(R.id.PASSWORD);
        passwordTextView.setText(pass);
        passwordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new PasswordFragment())
                        .commit();
            }
        });

        return view;
    }
}