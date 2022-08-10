package com.ganipohan.mymovieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ganipohan.mymovieapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, release_date, original_language;
    ImageView imageView;
    RatingBar ratingBar;
    ConstraintLayout layoutEmpty;

    //Click listener
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        title = itemView.findViewById(R.id.movie_title);
        release_date = itemView.findViewById(R.id.movie_category);
        original_language = itemView.findViewById(R.id.movie_duration);
        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        layoutEmpty = itemView.findViewById(R.id.layoutEmpty);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
