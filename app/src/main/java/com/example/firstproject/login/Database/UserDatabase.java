package com.example.firstproject.login.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.firstproject.login.Database.Model.model;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {model.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static Executor databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static UserDatabase INSTANCE;

    public abstract Dao dao();

    public static synchronized UserDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,
                            UserDatabase.class, "main_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
//                    .addCallback(RoomDatabaseCallback)
                    .build();

        }
        return INSTANCE;
    }
}
