package com.ganipohan.mymovieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.StatusBarManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ganipohan.mymovieapp.adapters.MovieRecyclerView;
import com.ganipohan.mymovieapp.adapters.OnMovieListener;
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

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    //add network security

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;


    //ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        SetupSearchView();

        recyclerView = findViewById(R.id.recyclerView);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        //Calling the observers
        ObserveAnyChange();
        ObservePopularMovies();

        //getting data for popular movies
        movieListViewModel.searchMoviePop(1);
    }

    private void ObservePopularMovies() {

        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing for any data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in log
                        movieRecyclerAdapter.setmMovies(movieModels);
                        Log.v("Tag", "Data Changed");
                    }
                }
            }
        });

    }

    //Observing any data change
    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing for any data change
                if (movieModels != null) {
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in log
                        movieRecyclerAdapter.setmMovies(movieModels);
                        Log.v("Tag", "Data Changed");
                    }
                }
            }
        });
    }

    //5-Initializing recyclerView & adding data to it
    private void ConfigureRecyclerView() {
        if (isPopular==true) {
            movieRecyclerAdapter = new MovieRecyclerView(this, isPopular);
            recyclerView.setAdapter(movieRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }else{
            movieRecyclerAdapter = new MovieRecyclerView(this, isPopular);
            recyclerView.setAdapter(movieRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            //RecyclerView Pagination
            //Loading next page of api response
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!recyclerView.canScrollVertically(1)) {
                        //Here we need to display the next search result on the next page of api
                        Log.v("Tag", "Scrolled");
                        movieListViewModel.searchNextpage();
                    }
                }
            });
        }

    }

    @Override
    public void onMovieClick(int positon) {
        //Toast.makeText(this, "The position clicked is "+positon,Toast.LENGTH_SHORT).show();
        //get ID of the novie in order to get detail movie
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(positon));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    //Get data from searchView & query the api to get the result (Movies)
    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //4-calling the method in mainActivity
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                searchView.clearFocus();
                isPopular = false;
                ConfigureRecyclerView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPopular = false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isPopular = true;
                //getting data for popular movies
                movieListViewModel.searchMoviePop(1);
                ConfigureRecyclerView();
                return false;
            }
        });


    }

}