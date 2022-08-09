package com.ganipohan.mymovieapp.ui.barang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.widget.ImageView;

import com.ganipohan.mymovieapp.R;
import com.ganipohan.mymovieapp.adapters.AdapterBarangRecyclerView;
import com.ganipohan.mymovieapp.models.Barang;
import com.ganipohan.mymovieapp.roomdb.AppDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomReadActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;
    private ImageView btnCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        /**
         * Initialize layout dan sebagainya
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        /**
         * Initialize ArrayList untuk data barang
         */
        daftarBarang = new ArrayList<>();

        /**
         * Initialize database
         * allow main thread queries
         */
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "barangdb").allowMainThreadQueries().build();

        /**
         * Initialize recyclerview dan layout manager
         */
        rvView = findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RoomCreateActivity.class);
            startActivity(intent);
        });

        /**
         * Add all data to arraylist
         */
        daftarBarang.addAll(Arrays.asList(db.barangDAO().selectAllBarangs()));

        /**
         * Set all data ke adapter, dan menampilkannya
         */
        adapter = new AdapterBarangRecyclerView(daftarBarang, this);
        rvView.setAdapter(adapter);
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, RoomReadActivity.class);
    }
}