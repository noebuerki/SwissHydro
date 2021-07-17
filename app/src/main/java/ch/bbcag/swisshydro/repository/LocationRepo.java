package ch.bbcag.swisshydro.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import ch.bbcag.swisshydro.repository.api.BafuApi;
import ch.bbcag.swisshydro.repository.database.AppDataBase;
import ch.bbcag.swisshydro.repository.database.dal.LocationDao;
import ch.bbcag.swisshydro.repository.database.objects.DataAge;
import ch.bbcag.swisshydro.repository.database.objects.Location;
import ch.bbcag.swisshydro.repository.database.objects.Measurement;
import ch.bbcag.swisshydro.repository.enums.DataType;


public class LocationRepo {

    private static final int MAX_LOCATION_AGE_IN_HOURS = 72;
    private static final int MAX_MEASUREMENT_AGE_IN_MINUTES = 10;
    private final Application application;

    private final DataAgeRepo dataAgeRepo;

    private final LocationDao locationDao;

    private final LiveData<List<Location>> allLocations;
    private final LiveData<List<Location>> favoriteLocations;

    private boolean isUpdating;

    public LocationRepo(Application application) {
        this.application = application;

        AppDataBase db = AppDataBase.getDatabase(application);
        locationDao = db.locationDao();
        allLocations = locationDao.getAll();
        favoriteLocations = locationDao.getFavorites();

        dataAgeRepo = new DataAgeRepo(application);
    }

    public LiveData<List<Location>> getAll() {
        AppDataBase.databaseWriteExecutor.execute(this::updateIfNecessary);
        return allLocations;
    }

    public LiveData<List<Location>> getFavorites() {
        return favoriteLocations;
    }

    public LiveData<Location> getByIdAsLiveData(String locationId) {
        return locationDao.getByIdAsLiveData(locationId);
    }

    public boolean changeIsFavorite(String locationId) {
        Location location = locationDao.getById(locationId);
        AtomicBoolean newFavoriteState = new AtomicBoolean(!location.isFavorite());
        AppDataBase.databaseWriteExecutor.execute(() -> locationDao.setFavorite(locationId, newFavoriteState.get()));
        return newFavoriteState.get();
    }

    private void updateIfNecessary() {
        if (!isUpdating) {
            isUpdating = true;

            DataAge locationAge = dataAgeRepo.getByType(DataType.LOCATION_DATA);
            DataAge measurementAge = dataAgeRepo.getByType(DataType.MEASUREMENT_DATA);
            long currentSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            long locationTimeDivHours = TimeUnit.SECONDS.toHours(currentSeconds - locationAge.getLastUpdate());
            long measurementTimeDivMinutes = TimeUnit.SECONDS.toMinutes(currentSeconds - measurementAge.getLastUpdate());

            if (locationTimeDivHours >= MAX_LOCATION_AGE_IN_HOURS) {
                BafuApi.getAllLocations(application, locations -> {
                    List<Location> oldLocations = new ArrayList<>();

                    if (allLocations.getValue() != null) {
                        oldLocations = allLocations.getValue();
                    }

                    for (Location location : locations) {
                        List<Location> filteredLocations = oldLocations.stream().filter(l -> location.getId().equals(l.getId())).collect(Collectors.toList());

                        if (filteredLocations.size() == 1) {
                            location.setFavorite(filteredLocations.get(0).isFavorite());
                            location.setMeasurement(filteredLocations.get(0).getMeasurement());
                        }
                    }

                    locationDao.deleteAll();
                    locationDao.insert(locations.toArray(new Location[0]));

                    BafuApi.getMeasurementsForLocations("", application, measurements -> {
                        for (Location location : locations) {
                            Location finalLocation = location;
                            List<Measurement> filteredMeasurements = measurements.stream().filter(m -> finalLocation.getId().equals(m.getLocationId())).collect(Collectors.toList());

                            if (filteredMeasurements.size() == 1) {
                                location = locationDao.getById(location.getId());
                                location.setMeasurementObject(filteredMeasurements.get(0));
                                locationDao.update(location);
                            }
                        }

                        locationAge.setLastUpdate(currentSeconds);
                        measurementAge.setLastUpdate(currentSeconds);

                        dataAgeRepo.update(locationAge);
                        dataAgeRepo.update(measurementAge);

                        isUpdating = false;
                    });
                });
            } else if (measurementTimeDivMinutes >= MAX_MEASUREMENT_AGE_IN_MINUTES) {
                BafuApi.getMeasurementsForLocations("", application, measurements -> {
                    for (Location location : Objects.requireNonNull(allLocations.getValue())) {
                        List<Measurement> filteredMeasurements = measurements.stream().filter(m -> location.getId().equals(m.getLocationId())).collect(Collectors.toList());

                        if (filteredMeasurements.size() == 1) {
                            location.setMeasurementObject(filteredMeasurements.get(0));
                            locationDao.update(location);
                        }
                    }

                    measurementAge.setLastUpdate(currentSeconds);
                    dataAgeRepo.update(measurementAge);

                    isUpdating = false;
                });
            }
        }
    }
}
