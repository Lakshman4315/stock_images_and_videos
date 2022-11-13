package com.example.firstproject.login.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.MainActivity;
import com.example.firstproject.R;
import com.example.firstproject.login.Database.Dao;
import com.example.firstproject.login.Database.UserDatabase;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity {

    MaterialTextView registerView ;
    EditText email,password;
    Button loginButton;

    Boolean succesfulLogin=false;

    Dao dao;

    private SharedPreferences myPreferences;
    private String sharedPrefFile = "com.example.android.firstProject";
    String LOGGED_IN = "loggedIn";

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor preferencesEditor = myPreferences.edit();
        preferencesEditor.putBoolean(LOGGED_IN, succesfulLogin);
        preferencesEditor.apply();

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        SharedPreferences.Editor preferencesEditor = myPreferences.edit();
//        preferencesEditor.putBoolean(LOGGED_IN, succesfulLogin);
//        preferencesEditor.apply();
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(myPreferences.getBoolean(LOGGED_IN, succesfulLogin)){
//            Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        if(myPreferences.getBoolean(LOGGED_IN, succesfulLogin)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        init();

//        UserDatabase user
        dao = UserDatabase.getDatabase(LoginActivity.this).dao();

        loginButton.setOnClickListener(view -> {
            String Email = email.getText().toString();
            String pass = password.getText().toString();


            if(Email.equals("") || pass.equals("")){
                Toast.makeText(LoginActivity.this,"Please Fill All Information",Toast.LENGTH_SHORT).show();
            }else{
                if(Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
//                    if(dao.emailValidate(Email)){
                        if(dao.login(Email,pass)){
                            Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();
                            succesfulLogin = true;
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(this,"Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                            succesfulLogin = false;
                        }
//                    }else{
//                        Toast.makeText(this,"Email does not exists",Toast.LENGTH_SHORT).show();
//                    }
                }else{
                    Toast.makeText(this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
                }
            }
        });


        registerView.setOnClickListener(view -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
            Toast.makeText(this,"CLICKED",Toast.LENGTH_SHORT).show();
        });
    }

    void init(){
        registerView = findViewById(R.id.login_register);
        loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
    }

    public void forgotPasswordListener(View view) {
    }


}