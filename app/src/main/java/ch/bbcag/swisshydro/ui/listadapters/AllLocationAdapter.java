package ch.bbcag.swisshydro.ui.listadapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.swisshydro.repository.database.objects.Location;
import ch.bbcag.swisshydro.ui.models.AllLocationModel;
import ch.bbcag.swisshydro.ui.viewholder.AllLocationHolder;

public class AllLocationAdapter extends ListAdapter<Location, AllLocationHolder> {

    private final AllLocationModel allLocationModel;

    private List<Location> completeList;

    public AllLocationAdapter(@NonNull DiffUtil.ItemCallback<Location> diffCallback, AllLocationModel allLocationModel) {
        super(diffCallback);

        this.allLocationModel = allLocationModel;
    }

    public void modifyList(List<Location> locations) {
        submitList(locations);
        completeList = locations;
    }

    public void filter(String filterString) {
        System.out.println(filterString);
        List<Location> locations = new ArrayList<>();

        if (filterString != null && !filterString.isEmpty()) {
            for (Location location : completeList) {
                if (location.getName().toLowerCase().contains(filterString.toLowerCase()) || location.getWaterBodyName().toLowerCase().contains(filterString.toLowerCase())) {
                    locations.add(location);
                }
            }

            submitList(locations);
        } else if (completeList != null) {
            submitList(locations);
            locations.addAll(completeList);
        }
    }

    @NonNull
    @Override
    public AllLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AllLocationHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AllLocationHolder holder, int position) {
        Location current = getItem(position);
        holder.bind(current);
        holder.getLocationStarIcon().setOnClickListener(allLocationModel);
    }

    public static class LocationDiff extends DiffUtil.ItemCallback<Location> {

        @Override
        public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            boolean idEquals = oldItem.getId().equals(newItem.getId());
            boolean nameEquals = oldItem.getName().equals(newItem.getName());
            boolean waterNameEquals = oldItem.getWaterBodyName().equals(newItem.getWaterBodyName());
            boolean waterTypeEquals = oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
            boolean measurementEquals;

            if (oldItem.getMeasurementObject() == null) {
                measurementEquals = false;
            } else {
                measurementEquals = oldItem.getMeasurement().equals(newItem.getMeasurement());
            }

            return idEquals && nameEquals && waterNameEquals && waterTypeEquals && measurementEquals;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            boolean idEquals = oldItem.getId().equals(newItem.getId());
            boolean nameEquals = oldItem.getName().equals(newItem.getName());
            boolean waterNameEquals = oldItem.getWaterBodyName().equals(newItem.getWaterBodyName());
            boolean waterTypeEquals = oldItem.getWaterBodyType().equals(newItem.getWaterBodyType());
            boolean favouriteEquals = oldItem.isFavorite() == newItem.isFavorite();
            boolean measurementEquals;

            if (oldItem.getMeasurementObject() == null) {
                measurementEquals = false;
            } else {
                measurementEquals = oldItem.getMeasurement().equals(newItem.getMeasurement());
            }

            return idEquals && nameEquals && waterNameEquals && waterTypeEquals && measurementEquals && favouriteEquals;
        }
    }
}
