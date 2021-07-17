package ch.bbcag.swisshydro.repository.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swisshydro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.bbcag.swisshydro.repository.database.objects.Location;
import ch.bbcag.swisshydro.repository.database.objects.Measurement;
import ch.bbcag.swisshydro.repository.enums.WaterBodyType;

public class BafuApi {

    private static final String BAFU_API_BASE_URL = "https://api.existenz.ch/apiv1/hydro/";
    private static final String BAFU_API_VERSION_INFO = "app=swisshydro&version=1.0.0";

    public static void getAllLocations(Context applicationContext, ApiCallback<List<Location>> callback) {
        String url = BAFU_API_BASE_URL + "locations?" + BAFU_API_VERSION_INFO;

        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject payloadObject = new JSONObject(response).getJSONObject("payload");

                List<Location> locations = new ArrayList<>();
                Iterator keyIterator = payloadObject.keys();

                while (keyIterator.hasNext()) {
                    JSONObject detailsObject = payloadObject.getJSONObject(keyIterator.next().toString()).getJSONObject("details");
                    Location location = new Location(
                            detailsObject.getString("id"),
                            detailsObject.getString("name"),
                            detailsObject.getString("water-body-name"),
                            WaterBodyType.valueOf(detailsObject.getString("water-body-type").toUpperCase())
                    );
                    locations.add(location);
                }

                callback.onSuccess(locations);
            } catch (JSONException e) {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(applicationContext, applicationContext.getString(R.string.server_error), Toast.LENGTH_LONG).show());

        requestQueue.add(stringRequest);
    }

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
                        measurement = new Measurement(valueObject.getString("loc"));
                        measurements.add(measurement);
                    }
                    double roundedValue = Math.round(valueObject.getDouble("val") * 100.0) / 100.0;
                    measurement.setProperty(valueObject.getString("par"), roundedValue);
                }

                callback.onSuccess(measurements);
            } catch (JSONException e) {
                Toast.makeText(applicationContext, applicationContext.getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(applicationContext, applicationContext.getString(R.string.server_error), Toast.LENGTH_LONG).show());

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(stringRequest);
    }

}
