package com.ganipohan.mymovieapp.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;
import com.ganipohan.mymovieapp.roomdb.AppDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FavoriteRepository {
    private AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public FavoriteRepository(Context context){
        appDatabase = AppDatabase.getInstance(context);
    }

    public void insertFavorite(FavoriteModel favoriteModel){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoriteDao().insertFavorite(favoriteModel);
            }
        });
    }

    public void updateFavorite(FavoriteModel favoriteModel){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoriteDao().updateFavorite(favoriteModel);
            }
        });
    }

    public void deleteFavorite(FavoriteModel favoriteModel){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoriteDao().deleteFavorite(favoriteModel);
            }
        });
    }

    public List<FavoriteModel> getAllFavoriteFuture() throws ExecutionException, InterruptedException{
        Callable<List<FavoriteModel>> callable = new Callable<List<FavoriteModel>>() {
            @Override
            public List<FavoriteModel> call() throws Exception {
                return appDatabase.favoriteDao().getAllFavoriteFuture();
            }
        };
        Future<List<FavoriteModel>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public LiveData<List<FavoriteModel>> getAllFavorite(){
        return appDatabase.favoriteDao().getAllFavoriteLive();
    }

//    public LiveData<List<FavoriteModel>> getFavorite(){
//        return appDatabase.favoriteDao().getFavorite();
//    }
}
