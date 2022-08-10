package com.ganipohan.mymovieapp.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteModel favoriteModel);

    @Update
    void updateFavorite(FavoriteModel favoriteModel);

    @Delete
    void deleteFavorite(FavoriteModel favoriteModel);

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteModel>> getAllFavoriteLive();

    @Query("SELECT * FROM favorite")
    List<FavoriteModel> getAllFavoriteFuture();

    @Query("SELECT * FROM favorite WHERE fId=:id")
    FavoriteModel getFavorite(int id);
}
