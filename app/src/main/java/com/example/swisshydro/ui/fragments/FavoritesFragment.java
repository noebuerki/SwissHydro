package com.example.swisshydro.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swisshydro.R;
import com.example.swisshydro.ui.listadapters.FavoriteLocationAdapter;
import com.example.swisshydro.ui.models.FavoriteLocationModel;

public class FavoritesFragment extends Fragment {

    private FavoriteLocationModel favoriteLocationModel;

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

        RecyclerView recyclerView = getView().findViewById(R.id.favList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        final FavoriteLocationAdapter adapter = new FavoriteLocationAdapter(new FavoriteLocationAdapter.LocationDiff());
        recyclerView.setAdapter(adapter);

        favoriteLocationModel = new ViewModelProvider(this).get(FavoriteLocationModel.class);
        favoriteLocationModel.getFavoriteLocations().observe(this, locations -> adapter.submitList(locations));
    }
}