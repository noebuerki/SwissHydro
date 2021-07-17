package com.example.swisshydro.ui.listadapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.swisshydro.repository.database.objects.Location;
import com.example.swisshydro.ui.viewholder.FavoriteLocationHolder;

public class FavoriteLocationAdapter extends ListAdapter<Location, FavoriteLocationHolder> {

    public FavoriteLocationAdapter(@NonNull DiffUtil.ItemCallback<Location> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public FavoriteLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FavoriteLocationHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteLocationHolder holder, int position) {
        Location current = getItem(position);

        holder.bind(current);
    }

    public static class LocationDiff extends DiffUtil.ItemCallback<Location> {

        @Override
        public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getId().equals(newItem.getId()) && oldItem.getName().equals(newItem.getName()) && oldItem.getWaterBodyName().equals(newItem.getWaterBodyName()) && oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
        }
    }
}
