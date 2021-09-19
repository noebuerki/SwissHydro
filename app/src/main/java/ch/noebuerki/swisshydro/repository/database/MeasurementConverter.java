package ch.noebuerki.swisshydro.repository.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ch.noebuerki.swisshydro.repository.database.objects.Measurement;

public class MeasurementConverter {

    private static final Gson gson = new Gson();
    private static final Type type = new TypeToken<Measurement>() {
    }.getType();

    public static String fromMeasurementToJson(Measurement measurement) {
        if (measurement == null) {
            return (null);
        }

        return gson.toJson(measurement, type);
    }

    public static Measurement fromJsonToMeasurement(String json) {
        if (json == null) {
            return (null);
        }

        return gson.fromJson(json, type);
    }
}
