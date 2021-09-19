package ch.noebuerki.swisshydro.ui.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.noebuerki.swisshydro.R;
import ch.noebuerki.swisshydro.repository.api.NetworkUtils;
import ch.noebuerki.swisshydro.ui.listadapters.AllLocationAdapter;
import ch.noebuerki.swisshydro.ui.models.AllLocationModel;

public class AllFragment extends Fragment implements SearchView.OnQueryTextListener {

    private AllLocationAdapter allLocationAdapter;
    private AllLocationModel allLocationModel;

    public static AllFragment newInstance() {
        return new AllFragment();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allLocationModel = new ViewModelProvider(this).get(AllLocationModel.class);
        allLocationAdapter = new AllLocationAdapter(new AllLocationAdapter.LocationDiff(), allLocationModel);

        RecyclerView recyclerView = view.findViewById(R.id.waterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(allLocationAdapter);

        ImageView allNothing = view.findViewById(R.id.all_nothing);
        TextView allNothingInfo = view.findViewById(R.id.all_nothing_info);

        allLocationModel.getAllLocations().observe(getViewLifecycleOwner(), locations -> {
            if (locations.isEmpty() && !NetworkUtils.getInstance().checkConnection()) {
                recyclerView.setVisibility(View.GONE);
                allNothing.setImageResource(R.drawable.no_data);
                allNothing.setVisibility(View.VISIBLE);
                allNothingInfo.setText(getString(R.string.no_data));
                allNothingInfo.setVisibility(View.VISIBLE);
            } else if (locations.isEmpty() && NetworkUtils.getInstance().checkConnection()) {
                recyclerView.setVisibility(View.GONE);
                allNothing.setImageResource(R.drawable.no_server);
                allNothing.setVisibility(View.VISIBLE);
                allNothingInfo.setText(getString(R.string.no_server));
                allNothingInfo.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                allNothing.setVisibility(View.GONE);
                allNothingInfo.setVisibility(View.GONE);
            }

            allLocationAdapter.modifyList(locations);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        allLocationAdapter.filter("");
        allLocationModel.getAllLocations();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        allLocationAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        allLocationAdapter.filter(newText);
        return true;
    }
}