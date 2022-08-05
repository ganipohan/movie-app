package com.ganipohan.mymovieapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

//    private static final int DISPLAY_POP = 1;
//    private static final int DISPLAY_SEARCH = 2;

    boolean isPopuler;

    public MovieRecyclerView(OnMovieListener onMovieListener, boolean b) {
        this.onMovieListener = onMovieListener;
        if (b){
            isPopuler = true;
        }else {
            isPopuler = false;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
//        if (viewType == DISPLAY_SEARCH){
        if (isPopuler == false){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                    parent, false);
            return new MovieViewHolder(view, onMovieListener);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie_layout,
                    parent, false);
            return new Popular_View_Holder(view, onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
//        if (itemViewType == DISPLAY_SEARCH){
        if (isPopuler==false){
            ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
            ((MovieViewHolder)holder).release_date.setText(mMovies.get(position).getRelease_date());
            ((MovieViewHolder)holder).original_language.setText(mMovies.get(position).getOriginal_language());

            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"
                            +mMovies.get(position).getBackdrop_path())
                    .placeholder(R.drawable.image_placeholder)
                    .into(((MovieViewHolder)holder).imageView);
        }else{

            ((Popular_View_Holder)holder).ratingBar2.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"
                            +mMovies.get(position).getPoster_path())
                    .placeholder(R.drawable.image_placeholder)
                    .into(((Popular_View_Holder)holder).imageView2);
        }

    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //getting the id of the movie clicked
    public MovieModel getSelectedMovie(int position){
        if (mMovies != null){
            return mMovies.get(position);
        }
        return null;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (Credentials.POPULAR){
//            return DISPLAY_POP;
//        }else
//            return DISPLAY_SEARCH;
//    }
}
