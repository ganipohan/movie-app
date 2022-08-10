package com.ganipohan.mymovieapp.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;

@Database(entities = {FavoriteModel.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABAE_NAME = "favorite.db";

    public static AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract FavoriteDao favoriteDao();

    public static AppDatabase getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABAE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

}
