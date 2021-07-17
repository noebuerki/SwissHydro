package com.example.swisshydro.ui.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swisshydro.R;
import com.example.swisshydro.repository.database.objects.Location;

public class FavoriteLocationHolder extends RecyclerView.ViewHolder {

    private final TextView locationItemView_Name;
    private final TextView locationItemView_Town;
    private final TextView locationItemView_Temp;

    private FavoriteLocationHolder(@NonNull View itemView) {
        super(itemView);
        locationItemView_Name = itemView.findViewById(R.id.fav_name);
        locationItemView_Town = itemView.findViewById(R.id.fav_town);
        locationItemView_Temp = itemView.findViewById(R.id.fav_temp);
    }

    public static FavoriteLocationHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_row_item, parent, false);
        return new FavoriteLocationHolder(view);
    }

    public void bind(Location location) {
        locationItemView_Name.setText(location.getWaterBodyName());
        locationItemView_Town.setText(location.getName());
        if (location.getMeasurementObject() == null) return;
        Double temp = location.getMeasurementObject().getTemperature();
        if (temp == null) {
            locationItemView_Temp.setText("-");
        } else {
            locationItemView_Temp.setText(temp + "°C");
        }
    }
}
