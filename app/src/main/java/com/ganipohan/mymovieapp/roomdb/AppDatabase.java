package com.ganipohan.mymovieapp.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ganipohan.mymovieapp.models.Barang;

@Database(entities = {Barang.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BarangDAO barangDAO();

}
