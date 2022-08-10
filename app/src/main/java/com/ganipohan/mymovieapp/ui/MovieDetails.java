package com.ganipohan.mymovieapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.models.Barang;
import com.ganipohan.mymovieapp.models.MovieModel;
import com.ganipohan.mymovieapp.roomdb.AppDatabase;

public class MovieDetails extends AppCompatActivity {

    private ImageView imageViewDetails, btnBack, addFav;
    private TextView titleDetails, descDetails;
    private RatingBar ratingBarDetails;

    private AppDatabase db;
    String title;
    String desc;
    String harga = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "barangdb").build();


        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textView_desc_details);
        ratingBarDetails = findViewById(R.id.ratingBar_details);
        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        addFav = findViewById(R.id.add_favorite);
        addFav.setOnClickListener(view -> {
            Barang barang = new Barang();
            barang.setNamaBarang(title);
            barang.setMerkBarang(desc);
            barang.setHargaBarang(harga);
            insertData(barang);
        });

        GetDataFromIntent();
    }
    @SuppressLint("StaticFieldLeak")
    private void insertData(final Barang barang) {

        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.barangDAO().insertBarang(barang);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(MovieDetails.this, "Ditambahkan ke daftar Favorite", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating(movieModel.getVote_average() / 2);

            title = titleDetails.getText().toString();
            desc = descDetails.getText().toString();

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(imageViewDetails);
        }
    }
}