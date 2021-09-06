package ch.bbcag.swisshydro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ch.bbcag.swisshydro.repository.api.NetworkUtils;
import ch.bbcag.swisshydro.repository.database.objects.Location;
import ch.bbcag.swisshydro.repository.database.objects.Measurement;
import ch.bbcag.swisshydro.repository.enums.WaterBodyType;
import ch.bbcag.swisshydro.ui.models.DetailsModel;

public class DetailsActivity extends AppCompatActivity {

    private DetailsModel detailsModel;
    private ImageView favoriteIndicator;
    private ImageView offlineIndicator;
    private TextView waterBodyName;
    private TextView cityName;
    private TextView waterBodyType;
    private TextView temperature;
    private TextView depth;
    private TextView height;
    private TextView flow_ls;
    private TextView flow_ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        favoriteIndicator = findViewById(R.id.details_favorite);
        favoriteIndicator.setOnClickListener(v -> {
            if (detailsModel.changeIsFavorite()) {
                ((ImageView) v).setImageResource(R.drawable.ic_star_white_36dp);
            } else {
                ((ImageView) v).setImageResource(R.drawable.ic_star_outline_white_36dp);
            }
        });

        offlineIndicator = findViewById(R.id.details_offline);
        waterBodyName = findViewById(R.id.details_water_name);
        cityName = findViewById(R.id.details_city);
        waterBodyType = findViewById(R.id.details_water_type);
        temperature = findViewById(R.id.details_temp_data);
        depth = findViewById(R.id.details_depth_data);
        height = findViewById(R.id.details_height_data);
        flow_ls = findViewById(R.id.details_flow_ls_data);
        flow_ms = findViewById(R.id.details_flow_ms_data);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (NetworkUtils.getInstance().checkConnection()) {
            offlineIndicator.setVisibility(View.INVISIBLE);
        } else {
            offlineIndicator.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        String locationId;

        if (!intent.hasExtra("locationId")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }

        locationId = intent.getStringExtra("locationId");
        detailsModel = new DetailsModel(getApplication(), locationId);
        detailsModel.getLocation().observe(this, this::updateUi);
    }

    private void updateUi(Location location) {
        if (location.isFavorite()) {
            favoriteIndicator.setImageResource(R.drawable.ic_star_white_36dp);
        } else {
            favoriteIndicator.setImageResource(R.drawable.ic_star_outline_white_36dp);
        }

        if (location.getWaterBodyName().length() > 20) {
            waterBodyName.setTextSize(24);
        }

        waterBodyName.setText(location.getWaterBodyName());
        cityName.setText(location.getName());

        if (location.getWaterBodyType().equals(WaterBodyType.RIVER)) {
            waterBodyType.setText(getString(R.string.water_type_river));
        } else {
            waterBodyType.setText(getString(R.string.water_type_lake));
        }

        Measurement measurement = location.getMeasurementObject();

        if (measurement != null) {
            if (measurement.getTemperature() != null) {
                String text = measurement.getTemperature() + " " + getString(R.string.temperature_unit);
                temperature.setText(text);
            } else {
                temperature.setText(getString(R.string.not_available));
            }

            if (measurement.getDepth() != null) {
                String text = measurement.getDepth() + " " + getString(R.string.distance_unit);
                depth.setText(text);
            } else {
                depth.setText(getString(R.string.not_available));
            }

            if (measurement.getHeight() != null) {
                String text = measurement.getHeight() + " " + getString(R.string.above_sea_level);
                height.setText(text);
            } else {
                height.setText(getString(R.string.not_available));
            }

            if (measurement.getFlow_ls() != null) {
                String text = measurement.getFlow_ls() + " " + getString(R.string.liters_per_second);
                flow_ls.setText(text);

                if (measurement.getFlow_ms() == null) {
                    text = measurement.getFlow_ls() / 1000 + " " + getString(R.string.cubic_meters_per_second);
                } else {
                    text = measurement.getFlow_ms() + " " + getString(R.string.cubic_meters_per_second);
                }

                flow_ms.setText(text);
            } else if (measurement.getFlow_ms() != null) {
                String text = measurement.getFlow_ms() + " " + getString(R.string.cubic_meters_per_second);
                flow_ms.setText(text);

                if (measurement.getFlow_ls() == null) {
                    text = measurement.getFlow_ms() * 1000 + " " + getString(R.string.liters_per_second);
                } else {
                    text = measurement.getFlow_ms() + " " + getString(R.string.cubic_meters_per_second);
                }
                flow_ls.setText(text);
            } else {
                flow_ls.setText(getString(R.string.not_available));
                flow_ms.setText(getString(R.string.not_available));
            }
        } else {
            temperature.setText(getString(R.string.not_available));
            depth.setText(getString(R.string.not_available));
            height.setText(getString(R.string.not_available));
            flow_ls.setText(getString(R.string.not_available));
            flow_ms.setText(getString(R.string.not_available));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}