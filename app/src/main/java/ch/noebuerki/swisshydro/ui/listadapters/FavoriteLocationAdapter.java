package ch.noebuerki.swisshydro.ui.listadapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ch.noebuerki.swisshydro.repository.database.objects.Location;
import ch.noebuerki.swisshydro.ui.viewholder.FavoriteLocationHolder;

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
            boolean idEquals = oldItem.getId().equals(newItem.getId());
            boolean nameEquals = oldItem.getName().equals(newItem.getName());
            boolean waterNameEquals = oldItem.getWaterBodyName().equals(newItem.getWaterBodyName());
            boolean waterTypeEquals = oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
            boolean isFavoriteEquals = oldItem.isFavorite() == newItem.isFavorite();
            boolean measurementEquals;

            if (oldItem.getMeasurementObject() == null && newItem.getMeasurementObject() != null) {
                measurementEquals = false;
            } else {
                measurementEquals = oldItem.getMeasurement().equals(newItem.getMeasurement());
            }

            return idEquals && nameEquals && waterNameEquals && waterTypeEquals && isFavoriteEquals && measurementEquals;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            boolean idEquals = oldItem.getId().equals(newItem.getId());
            boolean nameEquals = oldItem.getName().equals(newItem.getName());
            boolean waterNameEquals = oldItem.getWaterBodyName().equals(newItem.getWaterBodyName());
            boolean waterTypeEquals = oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
            boolean isFavoriteEquals = oldItem.isFavorite() == newItem.isFavorite();
            boolean measurementEquals;

            if (oldItem.getMeasurementObject() == null && newItem.getMeasurementObject() != null) {
                measurementEquals = false;
            } else {
                measurementEquals = oldItem.getMeasurement().equals(newItem.getMeasurement());
            }

            return idEquals && nameEquals && waterNameEquals && waterTypeEquals && isFavoriteEquals && measurementEquals;
        }
    }
}
