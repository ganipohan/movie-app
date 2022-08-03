package com.ganipohan.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.request.Servicey;
import com.ganipohan.mymovieapp.response.MovieSearchResponse;
import com.ganipohan.mymovieapp.utils.Credentials;
import com.ganipohan.mymovieapp.utils.MovieApi;
import com.ganipohan.mymovieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    //add network security

    Button btn;
    //ViewModel
    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //Calling the observers
        ObserveAnyChange();

        //Testing the method
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovieApi("fast", 1);
            }
        });
    }

    //Observing any data change
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing for any data change
                if (movieModels != null){
                    for (MovieModel movieModel: movieModels){
                        //Get the data in log
                        Log.v("Tagy", "onChange: "+movieModel.getTitle());
                    }
                }
            }
        });
    }

    //4-calling the method in mainActivity
    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query, pageNumber);
    }




//    private void GetRetrofitResponse() {
//        MovieApi movieApi = Servicey.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovie(
//                        Credentials.API_KEY,
//                        "Action",
//                        1);
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//
//                if (response.code() == 200){
////                    Log.v("Tag","the response" +response.body().toString());
//
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//                    for (MovieModel movie: movies){
//                        Log.v("Tag", "The release date "+movie.getTitle());
//                    }
//                }else{
//                    try {
//                        Log.v("Tag","Error" +response.errorBody().string());
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//
//    }
//    private void GetRetrofitResponseAccordingToId(){
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<MovieModel> responCall = movieApi.getMovie(550, Credentials.API_KEY);
//
//        responCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.code() == 200){
//                    MovieModel movie = response.body();
//                    Log.v("Tag", "The respon " +movie.getTitle());
//                }else {
//                    try {
//                        Log.v("Tag", "Error "+response.errorBody().string());
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }
}