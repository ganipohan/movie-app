package com.ganipohan.mymovieapp.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.models.favorite.FavoriteModel;
import com.ganipohan.mymovieapp.viewmodels.FavoriteViewModel;

public class FavoriteAddActivity extends AppCompatActivity {

    private String title, describ, images;
    private FavoriteViewModel favoriteViewModel;
    private FavoriteModel favoriteModel;
    private boolean isEdit = false;

    EditText edtTitle, edtDescrib, edtImageLink;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorite);

        favoriteViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(FavoriteViewModel.class);

        edtTitle = findViewById(R.id.edtTitle);
        edtDescrib = findViewById(R.id.edtDescrb);
        edtImageLink = findViewById(R.id.edtImages);
        btnSave = findViewById(R.id.btnAddFav);

        btnSave.setOnClickListener(view -> {
            title = edtTitle.getText().toString().trim();
            describ = edtDescrib.getText().toString().trim();
            images = edtImageLink.getText().toString().trim();

            sendData();
        });
    }

    private void sendData() {
        favoriteModel = new FavoriteModel();
        favoriteModel.title = title;
        favoriteModel.descrb = describ;
        favoriteModel.images = images;
        favoriteViewModel.insertFavorite(favoriteModel);
        Toast.makeText(getApplicationContext(), "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        finish();
    }
}