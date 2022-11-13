package com.example.firstproject.login.Database;


import android.webkit.SafeBrowsingResponse;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.firstproject.login.Database.Model.model;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * from user ORDER BY USERNAME ASC")
    LiveData<List<model>> getAlphabetizedWords();

    @Query("SELECT Exists (Select * from user where EMAIL=:email)")
    boolean emailValidate(String email);

    @Query("SELECT Exists (Select * from user where EMAIL=:email and PASSWORD =:password )")
    boolean login(String email, String password);

    @Insert
    void insert(model data);

    @Update
    void update(model data);

    @Delete
    void delete(model data);

    @Query("DELETE From user")
    void deleteAll();
}
