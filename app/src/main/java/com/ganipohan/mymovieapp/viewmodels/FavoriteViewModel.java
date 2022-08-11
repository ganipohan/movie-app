package com.ganipohan.mymovieapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;
import com.ganipohan.mymovieapp.repositories.FavoriteRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteRepository favoriteRepository;

    private MutableLiveData<FavoriteModel> favoriteModelMutableLiveData = new MutableLiveData<>();


    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        favoriteRepository = new FavoriteRepository(application);
    }

    public void insertFavorite(FavoriteModel favoriteModel){
        favoriteRepository.insertFavorite(favoriteModel);
    }

    public void updateFavorite(FavoriteModel favoriteModel){
        favoriteRepository.updateFavorite(favoriteModel);
    }

    public void deleteFavorite(FavoriteModel favoriteModel){
        favoriteRepository.deleteFavorite(favoriteModel);
    }

    public List<FavoriteModel> getAllProjectFuture() throws ExecutionException, InterruptedException{
        return favoriteRepository.getAllFavoriteFuture();
    }

    public LiveData<List<FavoriteModel>> getAllFavoriteLive(){
        return favoriteRepository.getAllFavorite();
    }

    public LiveData<FavoriteModel> getUserList() {
        return favoriteModelMutableLiveData;
    }
}
