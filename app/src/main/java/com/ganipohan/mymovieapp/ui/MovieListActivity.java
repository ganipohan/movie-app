package com.ganipohan.mymovieapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.adapters.MovieRecyclerView;
import com.ganipohan.mymovieapp.adapters.OnMovieListener;
import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.ui.favorite.FavoriteListActivity;
import com.ganipohan.mymovieapp.utils.Credentials;
import com.ganipohan.mymovieapp.viewmodels.MovieListViewModel;

import java.util.List;


public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    //add network security

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    //ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;
    ProgressBar progressBar;
    ImageView btn_favorite;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.proggress);
        cardView = findViewById(R.id.card_view);
        btn_favorite = findViewById(R.id.fav);
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavoriteListActivity.class);
                startActivity(intent);
            }
        });

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        SetupSearchView();

        recyclerView = findViewById(R.id.recyclerView);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        //Calling the observers
        ObservePopularMovies();
        ObserveAnyChange();

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
                    }
                }else{
                    Log.v("Tag", "List Kosong");
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
                    }
                }
            }
        });
    }

    //5-Initializing recyclerView & adding data to it
    private void ConfigureRecyclerView() {
        if (isPopular == true) {
            movieRecyclerAdapter = new MovieRecyclerView(this);
            Credentials.POPULAR=true;
            recyclerView.setAdapter(movieRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.clearOnScrollListeners();
            cardView.setVisibility(View.VISIBLE);
        } else {
            movieRecyclerAdapter = new MovieRecyclerView(this);
            Credentials.POPULAR=false;
            cardView.setVisibility(View.GONE);
            recyclerView.setAdapter(movieRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            //RecyclerView Pagination
            //Loading next page of api response
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                        progressBar.setVisibility(View.VISIBLE);
                        //Here we need to display the next search result on the next page of api
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                movieListViewModel.searchNextpage();
                            }
                        }, 3000);
                    }
                }
            });
        }

    }

    @Override
    public void onMovieClick(int positon) {
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
                recyclerView.clearOnScrollListeners();
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