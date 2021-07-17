package ch.bbcag.swisshydro.ui.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ch.bbcag.swisshydro.repository.LocationRepo;
import ch.bbcag.swisshydro.repository.database.objects.Location;

public class DetailsModel extends AndroidViewModel {

    private final LiveData<Location> location;
    private final LocationRepo locationRepo;

    public DetailsModel(Application application, String locationId) {
        super(application);

        locationRepo = new LocationRepo(application);
        location = locationRepo.getByIdAsLiveData(locationId);
    }

    public LiveData<Location> getLocation() {
        return location;
    }

    public boolean changeIsFavorite() {
        return locationRepo.changeIsFavorite(location.getValue().getId());
    }
}
