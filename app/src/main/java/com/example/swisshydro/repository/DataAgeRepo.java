package com.example.swisshydro.repository;

import android.app.Application;

import com.example.swisshydro.enums.DataType;
import com.example.swisshydro.repository.database.AppDataBase;
import com.example.swisshydro.repository.database.dal.DataAgeDao;
import com.example.swisshydro.repository.database.objects.DataAge;

public class DataAgeRepo {

    private final DataAgeDao dataAgeDao;

    public DataAgeRepo(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        dataAgeDao = db.dataAgeDao();
        prepareTable();
    }

    public void prepareTable() {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            DataAgeDao dao = dataAgeDao;

            DataAge locationAge = new DataAge();
            locationAge.type = DataType.LOCATION_DATA;
            locationAge.lastUpdate = 1L;
            dao.insertDataAge(locationAge);

            DataAge measurementAge = new DataAge();
            measurementAge.type = DataType.MEASUREMENT_DATA;
            measurementAge.lastUpdate = 1L;
            dao.insertDataAge(measurementAge);
        });
    }

    public DataAge getAgeByType(DataType type) {
        return dataAgeDao.getDataAgeByType(type);
    }

    public void updateDataAge(DataAge dataAge) {
        AppDataBase.databaseWriteExecutor.execute(() -> dataAgeDao.updateDataAge(dataAge));
    }
}
