package com.ganipohan.mymovieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ganipohan.mymovieapp.R;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView2;
    RatingBar ratingBar2;

    //Click listener
    OnMovieListener onMovieListener;

    public Popular_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageView2 = itemView.findViewById(R.id.movie_img2);
        ratingBar2 = itemView.findViewById(R.id.rating_bar2);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}