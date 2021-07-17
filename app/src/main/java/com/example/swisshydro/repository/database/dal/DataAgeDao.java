package com.example.swisshydro.repository.database.dal;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.swisshydro.enums.DataType;
import com.example.swisshydro.repository.database.objects.DataAge;


@Dao
public interface DataAgeDao {

    @Query("select * from dataage where type = :dataType")
    DataAge getDataAgeByType(DataType dataType);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDataAge(DataAge dataAge);

    @Update
    void updateDataAge(DataAge dataAge);
}
