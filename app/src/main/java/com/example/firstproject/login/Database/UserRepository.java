package com.example.firstproject.login.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.firstproject.login.Database.Model.model;

import java.util.List;

public class UserRepository {

    private Dao mDao;
    private LiveData<List<model>> mAlldata;
//    private List<model> userData;


    UserRepository(Application application){
        UserDatabase db = UserDatabase.getDatabase(application);
        mDao = db.dao();
        mAlldata = mDao.getAlphabetizedWords();
    }

//    UserRepository(Application application,String email){
//        UserDatabase db = UserDatabase.getDatabase(application);
//        mDao = db.dao();
//        userData = mDao.readAllData(email);
//    }

    LiveData<List<model>> getAllData(){
        return mAlldata;
    }

//    List<model> getUserData(){return userData;}

    void  insert(model data){
        UserDatabase.databaseWriteExecutor.execute(() ->
                mDao.insert(data));
    }
}
