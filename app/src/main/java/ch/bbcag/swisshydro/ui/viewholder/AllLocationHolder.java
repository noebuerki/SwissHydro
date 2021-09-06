package ch.bbcag.swisshydro.ui.viewholder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.bbcag.swisshydro.DetailsActivity;
import ch.bbcag.swisshydro.R;
import ch.bbcag.swisshydro.repository.database.objects.Location;
import ch.bbcag.swisshydro.repository.database.objects.Measurement;

public class AllLocationHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final TextView locationName;
    private final TextView locationTown;
    private final TextView locationTemperature;
    private final ImageView locationStarIcon;
    private int orientation;

    private AllLocationHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        locationName = itemView.findViewById(R.id.all_name);
        locationTown = itemView.findViewById(R.id.all_town);
        locationTemperature = itemView.findViewById(R.id.all_temp);
        locationStarIcon = itemView.findViewById(R.id.all_star);
    }

    public static AllLocationHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_row_item, parent, false);
        AllLocationHolder holder = new AllLocationHolder(view);
        holder.setOrientation(parent.getResources().getConfiguration().orientation);
        return holder;
    }

    public void bind(Location location) {
        String waterBodyName = location.getWaterBodyName();

        if (waterBodyName.length() >= 22 && orientation != 2) {
            waterBodyName = waterBodyName.substring(0, 22) + "…";
        }

        locationName.setText(waterBodyName);
        String townName = location.getName();

        if (townName.length() >= 28 && orientation != 2) {
            townName = townName.substring(0, 28) + "…";
        }

        locationTown.setText(townName);

        if (location.isFavorite()) {
            locationStarIcon.setImageResource(R.drawable.ic_star_blue_36dp);
        } else {
            locationStarIcon.setImageResource(R.drawable.ic_star_outline_blue_36dp);
        }

        locationStarIcon.setTag(location.getId());
        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);
            intent.putExtra("locationId", location.getId());
            itemView.getContext().startActivity(intent);
        });

        Measurement measurement = location.getMeasurementObject();

        if (measurement == null) {
            locationTemperature.setText("-");
        } else {
            Double temp = measurement.getTemperature();

            if (temp == null) {
                locationTemperature.setText("-");
            } else if (!(temp.toString() + "°C").contentEquals(locationTemperature.getText())) {
                String tempString = temp.toString() + "°C";
                locationTemperature.setText(tempString);
            }
        }
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public ImageView getLocationStarIcon() {
        return locationStarIcon;
    }
}
