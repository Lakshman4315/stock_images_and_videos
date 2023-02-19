package com.example.firstproject.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.ProfileActivity;
import com.example.firstproject.R;
import com.example.firstproject.login.Database.Dao;
import com.example.firstproject.login.Database.Model.model;
import com.example.firstproject.login.Database.UserDatabase;
import com.example.firstproject.login.view.policy.ConditionActivity;
import com.example.firstproject.login.view.policy.PolicyActivity;
import com.google.android.material.textview.MaterialTextView;

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,phoneNo,password;
    MaterialTextView loginView,condition,privacyPolicies;

    Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        dao = UserDatabase.getDatabase(this).dao();

        loginView.setOnClickListener(view -> {
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        condition.setOnClickListener(view ->
                startActivity(new Intent(RegisterActivity.this, ConditionActivity.class)));

        privacyPolicies.setOnClickListener(view ->
                startActivity(new Intent(RegisterActivity.this, PolicyActivity.class)));

    }

    void init(){
        loginView = findViewById(R.id.register_login);
        username = findViewById(R.id.register_username);
        email = findViewById(R.id.register_email);
        phoneNo = findViewById(R.id.register_phone_no);
        password = findViewById(R.id.register_password);
        condition = findViewById(R.id.condition);
        privacyPolicies = findViewById(R.id.policy);
    }

    public void RegisterListener(View view) {

        String userName = username.getText().toString();
        String Email = email.getText().toString();
        String phone = phoneNo.getText().toString();
        String pass = password.getText().toString();

        if(userName.equals("") || Email.equals("") || phone.equals("") || pass.equals("")){
            Toast.makeText(this,"Please Fill All Information",Toast.LENGTH_SHORT).show();
        }else{
           if(Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
               if (Patterns.PHONE.matcher(phone).matches()) {
                   if(pass.length()>=8){
                       dao.insert(new model(userName,Email,phone,pass));
                       startActivity(new Intent(this, ProfileActivity.class));
                       finish();
                   }else{
                       Toast.makeText(this,"Password Length Must Be Greater Than 8",Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(this,"Please Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
               }
           }else{
               Toast.makeText(this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
           }
        }
    }
}