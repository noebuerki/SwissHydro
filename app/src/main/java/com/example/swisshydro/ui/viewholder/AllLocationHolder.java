package com.example.swisshydro.ui.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swisshydro.R;
import com.example.swisshydro.repository.database.objects.Location;
import com.example.swisshydro.repository.database.objects.Measurement;
import com.example.swisshydro.ui.models.AllLocationModel;

public class AllLocationHolder extends RecyclerView.ViewHolder {

    private final TextView locationItemView_Name;
    private final TextView locationItemView_Town;
    private final TextView locationItemView_Temp;
    private final ImageView locationItemView_Star;

    private AllLocationHolder(@NonNull View itemView, AllLocationModel model) {
        super(itemView);
        locationItemView_Name = itemView.findViewById(R.id.all_name);
        locationItemView_Town = itemView.findViewById(R.id.all_town);
        locationItemView_Temp = itemView.findViewById(R.id.all_temp);
        locationItemView_Star = itemView.findViewById(R.id.all_star);
        locationItemView_Star.setOnClickListener(model);
    }

    public static AllLocationHolder create(ViewGroup parent, AllLocationModel model) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_row_item, parent, false);
        return new AllLocationHolder(view, model);
    }

    public void bind(Location location) {
        locationItemView_Name.setText(location.getWaterBodyName());
        locationItemView_Town.setText(location.getName());

        if (location.isFavorite()) {
            locationItemView_Star.setImageResource(R.drawable.round_star_black_48);
        } else {
            locationItemView_Star.setImageResource(R.drawable.round_star_outline_black_48);
        }
        locationItemView_Star.setTag(location.getId());
        Measurement measurement = location.getMeasurementObject();
        if (measurement == null) {
            locationItemView_Temp.setText("-");
        } else {
            Double temp = measurement.getTemperature();
            if (temp == null) {
                locationItemView_Temp.setText("-");
            } else if (!(temp.toString() + "°C").equals(locationItemView_Temp.getText())) {
                locationItemView_Temp.setText(temp.toString() + "°C");
            }
        }
    }
}
