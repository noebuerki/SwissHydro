package ch.noebuerki.swisshydro.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.noebuerki.swisshydro.R;
import ch.noebuerki.swisshydro.ui.listadapters.FavoriteLocationAdapter;
import ch.noebuerki.swisshydro.ui.models.FavoriteLocationModel;

public class FavoritesFragment extends Fragment {

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        FavoriteLocationModel favoriteLocationModel = new ViewModelProvider(this).get(FavoriteLocationModel.class);
        final FavoriteLocationAdapter adapter = new FavoriteLocationAdapter(new FavoriteLocationAdapter.LocationDiff());

        RecyclerView recyclerView = getView().findViewById(R.id.favList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        favoriteLocationModel.getFavoriteLocations().observe(this, locations -> {
            if (locations == null || locations.isEmpty()) {
                getView().findViewById(R.id.favList).setVisibility(View.GONE);
                getView().findViewById(R.id.fav_nothing).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.fav_nothing_info).setVisibility(View.VISIBLE);
            } else {
                getView().findViewById(R.id.favList).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.fav_nothing).setVisibility(View.GONE);
                getView().findViewById(R.id.fav_nothing_info).setVisibility(View.GONE);
                adapter.submitList(locations);
            }
        });
    }
}