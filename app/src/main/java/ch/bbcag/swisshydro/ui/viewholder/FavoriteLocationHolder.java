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
import ch.bbcag.swisshydro.repository.api.NetworkUtils;
import ch.bbcag.swisshydro.repository.database.objects.Location;

public class FavoriteLocationHolder extends RecyclerView.ViewHolder {

    private final View itemView;
    private final TextView locationName;
    private final TextView locationTown;
    private final TextView locationTemperature;
    private final ImageView locationOfflineIcon;

    private FavoriteLocationHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        locationName = itemView.findViewById(R.id.fav_name);
        locationTown = itemView.findViewById(R.id.fav_town);
        locationTemperature = itemView.findViewById(R.id.fav_temp);
        locationOfflineIcon = itemView.findViewById(R.id.fav_offline);
    }

    public static FavoriteLocationHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_row_item, parent, false);
        return new FavoriteLocationHolder(view);
    }

    public void bind(Location location) {
        locationName.setText(location.getWaterBodyName());
        locationTown.setText(location.getName());

        if (NetworkUtils.getInstance().checkConnection()) {
            locationOfflineIcon.setVisibility(View.INVISIBLE);
        } else {
            locationOfflineIcon.setVisibility(View.VISIBLE);
        }

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), DetailsActivity.class);
            intent.putExtra("locationId", location.getId());
            itemView.getContext().startActivity(intent);
        });

        if (location.getMeasurementObject() == null) return;

        Double temp = location.getMeasurementObject().getTemperature();

        if (temp == null) {
            locationTemperature.setText("-");
        } else {
            String tempString = temp.toString() + "°C";
            locationTemperature.setText(tempString);
        }
    }
}
