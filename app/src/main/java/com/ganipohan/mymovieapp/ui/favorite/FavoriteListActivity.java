package com.ganipohan.mymovieapp.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.adapters.favorite.FavoriteAdapter;
import com.ganipohan.mymovieapp.adapters.favorite.OnClickItemInterface;
import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;
import com.ganipohan.mymovieapp.viewmodels.FavoriteViewModel;

import java.util.List;

public class FavoriteListActivity extends AppCompatActivity implements OnClickItemInterface {

    private RecyclerView recyclerView;
    private ConstraintLayout emptyLayout;
    private FavoriteAdapter favoriteAdapter;
    ImageView btnBack;

    private FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.favoriteRecyclerView);
        emptyLayout = findViewById(R.id.layoutEmpty);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        ObservChange();
        SetRecyclerView();
    }
    public void ObservChange(){
        favoriteViewModel.getAllFavoriteLive().observe(this, new Observer<List<FavoriteModel>>() {
            @Override
            public void onChanged(List<FavoriteModel> favoriteModels) {
                if (favoriteModels != null){
                    for (FavoriteModel favoriteModel : favoriteModels){
                        favoriteAdapter.setmFavorite(favoriteModels);
                        emptyLayout.setVisibility(View.GONE);
                    }
                }else{
                    emptyLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void SetRecyclerView(){
        favoriteAdapter = new FavoriteAdapter( this);
        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.clearOnScrollListeners();
    }

    @Override
    public void onClickItem(int position) {
        Toast.makeText(getApplicationContext(), "position: "+String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}