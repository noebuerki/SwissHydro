package com.example.swisshydro.repository.database.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.swisshydro.enums.DataType;

@Entity(tableName = "dataage")
public class DataAge {

    @NonNull
    @PrimaryKey
    public DataType type;

    @ColumnInfo(name = "lastupdate")
    public Long lastUpdate;
}
