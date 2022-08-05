package com.ganipohan.mymovieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;
    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviesPop();
    }

    //2-calling the method in repository
    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(mQuery, mPageNumber);
    }

    public void searchMoviePop(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop(mPageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }

}
