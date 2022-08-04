package com.ganipohan.mymovieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.repositories.MovieRepository;

import java.util.List;

//class viewModel
public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    //constractor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    //3-calling the method in viewModel
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchNextpage(){
        movieRepository.searchNextPage();
    }
}
