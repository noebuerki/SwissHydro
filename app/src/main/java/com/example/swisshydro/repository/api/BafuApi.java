package com.example.swisshydro.repository.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swisshydro.enums.WaterBodyType;
import com.example.swisshydro.repository.database.objects.Location;
import com.example.swisshydro.repository.database.objects.Measurement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BafuApi {

    private static final String BAFU_API_BASE_URL = "https://api.existenz.ch/apiv1/hydro/";
    private static final String BAFU_API_VERSION_INFO = "app=swisshydro&version=1.0.0";

    // get all available locations
    public static void getAllLocations(Context applicationContext, ApiCallback<List<Location>> callback) {
        String url = BAFU_API_BASE_URL + "locations?" + BAFU_API_VERSION_INFO;

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject payloadObject = new JSONObject(response).getJSONObject("payload");

                List<Location> locations = new ArrayList<>();
                Iterator keyIterator = payloadObject.keys();

                while (keyIterator.hasNext()) {
                    JSONObject locationDetailsObject = payloadObject.getJSONObject(keyIterator.next().toString()).getJSONObject("details");
                    Location location = new Location(
                            locationDetailsObject.getString("id"),
                            locationDetailsObject.getString("name"),
                            locationDetailsObject.getString("water-body-name"),
                            WaterBodyType.valueOf(locationDetailsObject.getString("water-body-type").toUpperCase())
                    );
                    locations.add(location);
                }

                NetworkUtils.isConnected(true);
                callback.onSuccess(locations);
            } catch (JSONException e) {
                NetworkUtils.isConnected(false);
            }
        }, error -> {
            NetworkUtils.isConnected(false);
        });
        requestQueue.add(stringRequest);
    }

    // get the newest measurements from a single location
    public static void getMeasurementsForLocation(String locationId, Context applicationContext, ApiCallback<Measurement> callback) {
        String url = BAFU_API_BASE_URL + "latest?locations=" + locationId + "&" + BAFU_API_VERSION_INFO;

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray payloadArray = new JSONObject(response).getJSONArray("payload");
                Measurement measurement = null;

                for (int pos = 0; pos < payloadArray.length(); pos++) {
                    JSONObject valueObject = payloadArray.getJSONObject(pos);
                    if (measurement == null) {
                        measurement = new Measurement();
                        measurement.setLocationId(locationId);
                        measurement.setTimestamp(valueObject.getLong("timestamp"));
                    }
                    measurement.setProperty(valueObject.getString("par"), valueObject.getDouble("val"));
                }

                NetworkUtils.isConnected(true);
                callback.onSuccess(measurement);
            } catch (JSONException e) {
                NetworkUtils.isConnected(false);
            }
        }, error -> {
            NetworkUtils.isConnected(false);
        });

        requestQueue.add(stringRequest);
    }

    // get the newest measurements from multiple locations
    public static void getMeasurementsForLocations(String locationIds, Context applicationContext, ApiCallback<List<Measurement>> callback) {
        String url = BAFU_API_BASE_URL + "latest?locations=" + locationIds + "&" + BAFU_API_VERSION_INFO;

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray payloadArray = new JSONObject(response).getJSONArray("payload");
                List<Measurement> measurements = new ArrayList<>();

                Measurement measurement = null;

                for (int pos = 0; pos < payloadArray.length(); pos++) {
                    JSONObject valueObject = payloadArray.getJSONObject(pos);
                    if (measurement == null || !valueObject.getString("loc").equals(measurement.getLocationId())) {
                        measurement = new Measurement();
                        measurement.setLocationId(valueObject.getString("loc"));
                        measurement.setTimestamp(valueObject.getLong("timestamp"));
                        measurements.add(measurement);
                    }
                    measurement.setProperty(valueObject.getString("par"), valueObject.getDouble("val"));
                }

                NetworkUtils.isConnected(true);
                callback.onSuccess(measurements);
            } catch (JSONException e) {
                NetworkUtils.isConnected(false);
            }
        }, error -> {
            NetworkUtils.isConnected(false);
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(stringRequest);
    }

}
