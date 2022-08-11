package com.ganipohan.mymovieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;
import com.ganipohan.mymovieapp.response.MovieSearchResponse;
import com.ganipohan.mymovieapp.ui.favorite.FavoriteListActivity;
import com.ganipohan.mymovieapp.viewmodels.FavoriteViewModel;
import com.ganipohan.mymovieapp.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    private FavoriteViewModel favoriteViewModel;
    private FavoriteModel favoriteModel;

    private ImageView imageViewDetails, btnBack, addFav;
    private TextView titleDetails, descDetails;
    private RatingBar ratingBarDetails;

    private String title, describ, images,vote_average;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        favoriteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(FavoriteViewModel.class);

        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textView_desc_details);
        ratingBarDetails = findViewById(R.id.ratingBar_details);
        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        addFav = findViewById(R.id.add_favorite);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating(movieModel.getVote_average() / 2);

            title = titleDetails.getText().toString();
            describ = descDetails.getText().toString();
            images = movieModel.getPoster_path();
            vote_average = String.valueOf(movieModel.getVote_average());

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(imageViewDetails);

            addFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteModel = new FavoriteModel();
                    favoriteModel.title = title;
                    favoriteModel.descrb = describ;
                    favoriteModel.images = images;
                    favoriteModel.vote = vote_average;

                    favoriteViewModel.insertFavorite(favoriteModel);
                    Toast.makeText(getApplicationContext(), "Favorite", Toast.LENGTH_SHORT).show();
                }
            });
        }else if (getIntent().hasExtra("fav")) {
            favoriteModel = getIntent().getParcelableExtra("fav");

            titleDetails.setText(favoriteModel.title);
            descDetails.setText(favoriteModel.descrb);
            ratingBarDetails.setRating(Float.parseFloat(favoriteModel.vote)/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + favoriteModel.images)
                    .into(imageViewDetails);

            addFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
            addFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteViewModel = new ViewModelProvider(MovieDetails.this).get(FavoriteViewModel.class);
                    favoriteViewModel.deleteFavorite(favoriteModel);

                    Toast.makeText(getApplicationContext(), "Unfavorite", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}