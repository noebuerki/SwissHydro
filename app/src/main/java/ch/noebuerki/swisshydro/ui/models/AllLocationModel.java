package ch.noebuerki.swisshydro.ui.models;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ch.noebuerki.swisshydro.R;
import ch.noebuerki.swisshydro.repository.LocationRepo;
import ch.noebuerki.swisshydro.repository.database.objects.Location;

public class AllLocationModel extends AndroidViewModel implements View.OnClickListener {

    private final LocationRepo locationRepo;

    public AllLocationModel(Application application) {
        super(application);

        locationRepo = new LocationRepo(application);
    }

    public LiveData<List<Location>> getAllLocations() {
        return locationRepo.getAll();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.all_star) {
            String id = (String) v.getTag();

            if (id != null && !id.equals("")) {
                if (locationRepo.changeIsFavorite(id)) {
                    ((ImageView) v).setImageResource(R.drawable.ic_star_blue_36dp);
                } else {
                    ((ImageView) v).setImageResource(R.drawable.ic_star_outline_blue_36dp);
                }
            }
        }
    }
}
