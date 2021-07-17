package com.example.swisshydro.repository.database.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.swisshydro.enums.WaterBodyType;
import com.example.swisshydro.repository.database.MeasurementConverter;

@Entity(tableName = "location")
public class Location {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "is-favorite")
    private boolean isFavorite = false;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "water-body-name")
    private String waterBodyName;

    @NonNull
    @ColumnInfo(name = "water-body-type")
    private WaterBodyType waterBodyType;

    @ColumnInfo(name = "measurement")
    private String measurement;

    public Location(@NonNull String id, @NonNull String name, @NonNull String waterBodyName, @NonNull WaterBodyType waterBodyType) {
        this.id = id;
        this.name = name;
        this.waterBodyName = waterBodyName;
        this.waterBodyType = waterBodyType;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getWaterBodyName() {
        return waterBodyName;
    }

    public void setWaterBodyName(@NonNull String waterBodyName) {
        this.waterBodyName = waterBodyName;
    }

    @NonNull
    public WaterBodyType getWaterBodyType() {
        return waterBodyType;
    }

    public void setWaterBodyType(@NonNull WaterBodyType waterBodyType) {
        this.waterBodyType = waterBodyType;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Measurement getMeasurementObject() {
        return MeasurementConverter.fromStringToMeasurement(measurement);
    }

    public void setMeasurementObject(Measurement measurement) {
        this.measurement = MeasurementConverter.fromMeasurementToString(measurement);
    }
}
