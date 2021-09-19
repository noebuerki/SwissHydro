package ch.noebuerki.swisshydro.repository.database.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ch.noebuerki.swisshydro.repository.enums.DataType;

@Entity(tableName = "dataage")
public class DataAge {

    @NonNull
    @PrimaryKey
    private final DataType type;

    @ColumnInfo(name = "lastupdate")
    private Long lastUpdate;

    public DataAge(@NonNull DataType type, Long lastUpdate) {
        this.type = type;
        this.lastUpdate = lastUpdate;
    }

    @NonNull
    public DataType getType() {
        return type;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
