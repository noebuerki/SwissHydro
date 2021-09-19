package ch.noebuerki.swisshydro.repository.database.dal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.noebuerki.swisshydro.repository.database.objects.Location;

@Dao
public interface LocationDao {

    @Query("select * from location order by `water-body-name` asc, name asc")
    LiveData<List<Location>> getAll();

    @Query("select * from location where `is-favorite` = 1 order by `water-body-name` asc, name asc")
    LiveData<List<Location>> getFavorites();

    @Query("select * from location where id = :locationId")
    LiveData<Location> getByIdAsLiveData(String locationId);

    @Query("select * from location where id = :locationId")
    Location getById(String locationId);

    @Insert
    void insert(Location... locations);

    @Update
    void update(Location location);

    @Query("update location set `is-favorite` = :isFavorite where id = :locationId")
    void setFavorite(String locationId, boolean isFavorite);

    @Query("delete from location")
    void deleteAll();
}
