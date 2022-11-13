package com.example.firstproject.login.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.firstproject.login.Database.Model.model;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    private LiveData<List<model>> mAllData;

    private List<model> userData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        mAllData = userRepository.getAllData();
    }
//
//    public UserViewModel(Application application, String email){
//        super(application);
//        userRepository = new UserRepository(application);
//        userData = userRepository.getUserData();
//    }

    LiveData<List<model>> getAllData(){
        return mAllData;
    }

//    public List<model> getUserData(){return userData;}

    public void insert(model data){
        userRepository.insert(data);
    }

}
