package com.ganipohan.mymovieapp.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ganipohan.mymovieapp.models.Barang;

@Dao
public interface BarangDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBarang(Barang barang);

    @Update
    int updateBarang(Barang barang);

    @Delete
    int deleteBarang(Barang barang);

    @Query("SELECT * FROM tbarang ORDER BY nama_barang ASC")
    Barang[] selectAllBarangs();

    @Query("SELECT * FROM tbarang WHERE barangId = :id LIMIT 1")
    Barang selectBarangDetail(int id);

}
