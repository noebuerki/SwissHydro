package com.example.swisshydro.ui.models;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.swisshydro.R;
import com.example.swisshydro.repository.LocationRepo;
import com.example.swisshydro.repository.database.objects.Location;

import java.util.List;

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
                    ((ImageView) v).setImageResource(R.drawable.round_star_black_48);
                } else {
                    ((ImageView) v).setImageResource(R.drawable.round_star_outline_black_48);
                }
            }
        }
    }
}
