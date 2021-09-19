package ch.noebuerki.swisshydro.ui.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ch.noebuerki.swisshydro.repository.LocationRepo;
import ch.noebuerki.swisshydro.repository.database.objects.Location;

public class FavoriteLocationModel extends AndroidViewModel {

    private final LocationRepo locationRepo;

    public FavoriteLocationModel(Application application) {
        super(application);

        locationRepo = new LocationRepo(application);
    }

    public LiveData<List<Location>> getFavoriteLocations() {
        return locationRepo.getFavorites();
    }
}
