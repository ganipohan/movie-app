package com.ganipohan.mymovieapp.adapters.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import com.bumptech.glide.Glide;
import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;
import com.ganipohan.mymovieapp.ui.MovieDetails;
import com.ganipohan.mymovieapp.viewmodels.FavoriteViewModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private boolean islayout=false;
    private FavoriteViewModel favoriteViewModel;
    private List<FavoriteModel> favoriteModelList;
    private OnClickItemInterface onClickItemInterface;

    public FavoriteAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorite, parent, false);
        context = parent.getContext();
        return new ViewHolder(v, onClickItemInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(favoriteModelList.get(position).title);
        holder.description.setText(favoriteModelList.get(position).descrb);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        +favoriteModelList.get(position).images)
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (islayout==false){
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("fav", favoriteModelList.get(position));
                    context.startActivity(intent);
                }else{
                    holder.layout.setVisibility(View.GONE);
                    islayout=false;
                }
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (islayout==false) {
                    holder.layout.setVisibility(View.VISIBLE);
                    islayout=true;
                }
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (favoriteModelList != null) {
                            deteleFavorite(position);
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favoriteModelList != null){
            return favoriteModelList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;
        CardView cardView;
        ImageView btnDelete;
        LinearLayout layout;

        OnClickItemInterface onClickItemInterface;

        ViewHolder(@NonNull View itemView, OnClickItemInterface onClickItemInterface){
            super(itemView);

            this.onClickItemInterface = onClickItemInterface;

            title = itemView.findViewById(R.id.fav_title);
            description = itemView.findViewById(R.id.fav_category);
            imageView = itemView.findViewById(R.id.movie_img);
            layout = itemView.findViewById(R.id.lay_button);
            btnDelete = itemView.findViewById(R.id.btn_del);
            cardView = itemView.findViewById(R.id.card_view);

//            itemView.setOnClickListener(this);
        }

//        public void onClick(View view) {
//            onClickItemInterface.onClickItem(getAdapterPosition());
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setmFavorite(List<FavoriteModel> favoriteModelList) {
        this.favoriteModelList = favoriteModelList;
        notifyDataSetChanged();
    }

    private void deteleFavorite(int position){
        islayout=false;
        favoriteViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(FavoriteViewModel.class);
        favoriteViewModel.deleteFavorite(favoriteModelList.get(position));
        favoriteModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, favoriteModelList.size());
    }


    //getting the id of the movie clicked
    public FavoriteModel getSelectedFavorite(int position){
        if (favoriteModelList != null){
            return favoriteModelList.get(position);
        }
        return null;
    }
}
