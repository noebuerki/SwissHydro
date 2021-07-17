package com.example.swisshydro.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.swisshydro.enums.DataType;
import com.example.swisshydro.repository.api.ApiCallback;
import com.example.swisshydro.repository.api.BafuApi;
import com.example.swisshydro.repository.database.AppDataBase;
import com.example.swisshydro.repository.database.dal.LocationDao;
import com.example.swisshydro.repository.database.objects.DataAge;
import com.example.swisshydro.repository.database.objects.Location;
import com.example.swisshydro.repository.database.objects.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


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
        AppDataBase.databaseWriteExecutor.execute(() -> updateIfNecessary());

        return allLocations;
    }

    public LiveData<List<Location>> getFavoriteLocations() {

        return favoriteLocations;
    }

    public boolean changeIsFavorite(String locationId) {
        AtomicBoolean newFavoriteState = new AtomicBoolean(!locationDao.getById(locationId).isFavorite());
        AppDataBase.databaseWriteExecutor.execute(() -> {
            locationDao.setFavorite(locationId, newFavoriteState.get());
        });

        return newFavoriteState.get();
    }

    public void insertLocation(Location location) {
        AppDataBase.databaseWriteExecutor.execute(() -> locationDao.insertLocation(location));
    }

    public void insertLocations(Location[] locations) {
        AppDataBase.databaseWriteExecutor.execute(() -> locationDao.insertLocation(locations));
    }

    private void updateIfNecessary() {

        if (!isUpdating) {
            isUpdating = true;

            DataAge locationAge = dataAgeRepo.getAgeByType(DataType.LOCATION_DATA);
            DataAge measurementAge = dataAgeRepo.getAgeByType(DataType.MEASUREMENT_DATA);
            long currentSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            long locationTimeDivHours = TimeUnit.SECONDS.toHours(currentSeconds - locationAge.lastUpdate);
            long measurementTimeDivMinutes = TimeUnit.SECONDS.toMinutes(currentSeconds - measurementAge.lastUpdate);

            System.out.println("H:" + locationTimeDivHours + " M:" + measurementTimeDivMinutes);

            if (locationTimeDivHours >= MAX_LOCATION_AGE_IN_HOURS) {
                System.out.println("GOT BOTH");
                BafuApi.getAllLocations(application, new ApiCallback<List<Location>>() {
                    @Override
                    public void onSuccess(List<Location> response) {
                        List<Location> responseLocations = response;
                        List<Location> oldLocations = new ArrayList<>();

                        if (allLocations.getValue() != null) {
                            oldLocations = allLocations.getValue();
                        }
                        for (Location responseLocation : responseLocations) {
                            for (Location oldLocation : oldLocations) {
                                if (responseLocation.getId().equals(oldLocation.getId())) {
                                    responseLocation.setFavorite(oldLocation.isFavorite());
                                    break;
                                }
                            }
                        }

                        locationDao.deleteAll();
                        locationDao.insertLocation(responseLocations.toArray(new Location[0]));

                        BafuApi.getMeasurementsForLocations("", application, new ApiCallback<List<Measurement>>() {
                            @Override
                            public void onSuccess(List<Measurement> response) {
                                for (Location responseLocation : responseLocations) {
                                    for (Measurement measurement : response) {
                                        if (measurement.getLocationId().equals(responseLocation.getId())) {
                                            responseLocation.setMeasurementObject(measurement);
                                            locationDao.updateLocation(responseLocation);
                                            break;
                                        }
                                    }
                                }

                                locationAge.lastUpdate = currentSeconds;
                                measurementAge.lastUpdate = currentSeconds;

                                dataAgeRepo.updateDataAge(locationAge);
                                dataAgeRepo.updateDataAge(measurementAge);

                                isUpdating = false;
                            }
                        });
                    }
                });
            } else if (measurementTimeDivMinutes >= MAX_MEASUREMENT_AGE_IN_MINUTES) {
                System.out.println("GOT MEASUREMENTS");
                BafuApi.getMeasurementsForLocations("", application, new ApiCallback<List<Measurement>>() {
                    @Override
                    public void onSuccess(List<Measurement> response) {
                        for (Location location : Objects.requireNonNull(allLocations.getValue())) {
                            for (Measurement measurement : response) {
                                if (measurement.getLocationId().equals(location.getId())) {
                                    location.setMeasurementObject(measurement);
                                    locationDao.updateLocation(location);
                                    break;
                                }
                            }
                        }
                        measurementAge.lastUpdate = currentSeconds;

                        dataAgeRepo.updateDataAge(measurementAge);

                        isUpdating = false;
                    }
                });
            }
        }
    }
}
