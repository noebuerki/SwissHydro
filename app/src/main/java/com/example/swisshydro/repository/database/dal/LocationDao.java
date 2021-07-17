package com.example.swisshydro.repository.database.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.swisshydro.repository.database.objects.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("select * from location order by `water-body-name` asc, name asc")
    LiveData<List<Location>> getAll();

    @Query("select * from location where id = :locationId")
    Location getById(String locationId);

    @Query("select * from location where `is-favorite` = 1 order by `water-body-name` asc, name asc")
    LiveData<List<Location>> getFavorites();

    @Query("update location set `is-favorite` = :isFavorite where id = :locationId")
    void setFavorite(String locationId, boolean isFavorite);

    @Insert
    void insertLocation(Location... locations);

    @Update
    void updateLocation(Location location);

    @Query("delete from location")
    void deleteAll();
}
