package com.example.swisshydro.ui.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.swisshydro.repository.LocationRepo;
import com.example.swisshydro.repository.database.objects.Location;

import java.util.List;

public class FavoriteLocationModel extends AndroidViewModel {

    private final LocationRepo locationRepo;

    public FavoriteLocationModel(Application application) {
        super(application);

        locationRepo = new LocationRepo(application);
    }

    public LiveData<List<Location>> getFavoriteLocations() {
        return locationRepo.getFavoriteLocations();
    }
}
