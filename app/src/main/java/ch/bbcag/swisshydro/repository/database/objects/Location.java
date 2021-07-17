package ch.bbcag.swisshydro.repository.database.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ch.bbcag.swisshydro.repository.database.MeasurementConverter;
import ch.bbcag.swisshydro.repository.enums.WaterBodyType;

@Entity(tableName = "location")
public class Location {

    @NonNull
    @PrimaryKey
    private final String id;

    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    @NonNull
    @ColumnInfo(name = "water-body-name")
    private final String waterBodyName;

    @NonNull
    @ColumnInfo(name = "water-body-type")
    private final WaterBodyType waterBodyType;

    @ColumnInfo(name = "is-favorite")
    private boolean isFavorite = false;

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

    @NonNull
    public String getWaterBodyName() {
        return waterBodyName;
    }

    @NonNull
    public WaterBodyType getWaterBodyType() {
        return waterBodyType;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Measurement getMeasurementObject() {
        return MeasurementConverter.fromJsonToMeasurement(measurement);
    }

    public void setMeasurementObject(Measurement measurement) {
        this.measurement = MeasurementConverter.fromMeasurementToJson(measurement);
    }
}
