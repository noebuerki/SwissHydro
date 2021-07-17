package com.example.swisshydro.repository.database;

import com.example.swisshydro.repository.database.objects.Measurement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MeasurementConverter {

    public static String fromMeasurementToString(Measurement measurement) {
        if (measurement == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Measurement>() {
        }.getType();
        String json = gson.toJson(measurement, type);
        return json;
    }

    public static Measurement fromStringToMeasurement(String json) {
        if (json == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Measurement>() {
        }.getType();
        Measurement measurement = gson.fromJson(json, type);
        return measurement;
    }
}
