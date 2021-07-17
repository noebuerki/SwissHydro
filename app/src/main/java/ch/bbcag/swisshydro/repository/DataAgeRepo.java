package ch.bbcag.swisshydro.repository;

import android.app.Application;

import ch.bbcag.swisshydro.repository.database.AppDataBase;
import ch.bbcag.swisshydro.repository.database.dal.DataAgeDao;
import ch.bbcag.swisshydro.repository.database.objects.DataAge;
import ch.bbcag.swisshydro.repository.enums.DataType;

public class DataAgeRepo {

    private final DataAgeDao dataAgeDao;

    public DataAgeRepo(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        dataAgeDao = db.dataAgeDao();
        prepareTable();
    }

    public void prepareTable() {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            DataAge locationAge = new DataAge(DataType.LOCATION_DATA, 1L);
            dataAgeDao.insert(locationAge);

            DataAge measurementAge = new DataAge(DataType.MEASUREMENT_DATA, 1L);
            dataAgeDao.insert(measurementAge);
        });
    }

    public DataAge getByType(DataType type) {
        return dataAgeDao.getByType(type);
    }

    public void update(DataAge dataAge) {
        AppDataBase.databaseWriteExecutor.execute(() -> dataAgeDao.update(dataAge));
    }
}
