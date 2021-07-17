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
import com.example.swisshydro.ui.listadapters.AllLocationAdapter;
import com.example.swisshydro.ui.models.AllLocationModel;

public class AllFragment extends Fragment {

    private AllLocationModel allLocationModel;

    public static AllFragment newInstance() {
        return new AllFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        allLocationModel = new ViewModelProvider(this).get(AllLocationModel.class);

        RecyclerView recyclerView = getView().findViewById(R.id.watList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        final AllLocationAdapter adapter = new AllLocationAdapter(new AllLocationAdapter.LocationDiff(), allLocationModel);
        recyclerView.setAdapter(adapter);

        allLocationModel.getAllLocations().observe(this, locations -> adapter.submitList(locations));
    }
}