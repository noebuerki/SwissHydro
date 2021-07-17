package ch.bbcag.swisshydro.repository.database.dal;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import ch.bbcag.swisshydro.repository.database.objects.DataAge;
import ch.bbcag.swisshydro.repository.enums.DataType;


@Dao
public interface DataAgeDao {

    @Query("select * from dataage where type = :dataType")
    DataAge getByType(DataType dataType);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DataAge dataAge);

    @Update
    void update(DataAge dataAge);
}
