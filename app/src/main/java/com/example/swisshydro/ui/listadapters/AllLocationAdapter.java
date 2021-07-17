package com.example.swisshydro.ui.listadapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.swisshydro.repository.database.objects.Location;
import com.example.swisshydro.ui.models.AllLocationModel;
import com.example.swisshydro.ui.viewholder.AllLocationHolder;

public class AllLocationAdapter extends ListAdapter<Location, AllLocationHolder> {

    private final AllLocationModel allLocationModel;

    public AllLocationAdapter(@NonNull DiffUtil.ItemCallback<Location> diffCallback, AllLocationModel allLocationModel) {
        super(diffCallback);
        this.allLocationModel = allLocationModel;
    }

    @NonNull
    @Override
    public AllLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AllLocationHolder.create(parent, allLocationModel);
    }

    @Override
    public void onBindViewHolder(@NonNull AllLocationHolder holder, int position) {
        Location current = getItem(position);
        holder.bind(current);
    }

    public static class LocationDiff extends DiffUtil.ItemCallback<Location> {

        @Override
        public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getId().equals(newItem.getId()) && oldItem.getName().equals(newItem.getName()) && oldItem.getWaterBodyName().equals(newItem.getWaterBodyName()) && oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getId().equals(newItem.getId()) && oldItem.getName().equals(newItem.getName()) && oldItem.getWaterBodyName().equals(newItem.getWaterBodyName()) && oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
        }
    }
}
