package com.example.stressmessungpuls.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PulsedataDao {
    @Query("SELECT * FROM pulsedata")
    List<Pulsedata> getAll();

    @Query("SELECT * FROM pulsedata WHERE id IN (:id)")
    List<Pulsedata> loadAllByIds(int[] id);

    @Query("SELECT * FROM pulsedata WHERE pulsevalue LIKE :pulsevalue AND " +
            "city LIKE :city AND " +
            "datetime LIKE :datetime LIMIT 1")
    Pulsedata findbyDate (int pulsevalue, String city, String datetime);

    @Query("SELECT AVG (pulsevalue) from pulsedata")
    int getAverage();

    @Insert
    void insertAll(Pulsedata... pulsedataplural);

    @Delete
    void delete(Pulsedata pulsedata);
}
