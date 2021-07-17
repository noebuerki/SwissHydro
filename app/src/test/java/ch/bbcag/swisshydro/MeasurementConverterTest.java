package ch.bbcag.swisshydro;

import ch.bbcag.swisshydro.repository.database.MeasurementConverter;
import ch.bbcag.swisshydro.repository.database.objects.Measurement;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasurementConverterTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 50, 13, 445", "2, 75, 15, 455", "3, 100, 17, 556"}, delimiter = ',')
    public void fromMeasurementToJson(Integer locationId, Double flow, Double temperature, Double height){
        Measurement measurement = new Measurement(locationId.toString());
        measurement.setProperty("flow", flow);
        measurement.setProperty("temperature", temperature);
        measurement.setProperty("height", height);
        String converted = MeasurementConverter.fromMeasurementToJson(measurement);
        assertEquals("{\"locationId\":\"" + locationId + "\",\"flow_ms\":" + flow + ",\"height\":" + height + ",\"temperature\":" + temperature + "}", converted);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 50, 13, 445", "2, 75, 15, 455", "3, 100, 17, 556"}, delimiter = ',')
    public void fromJsonToMeasurement(String locationId, Double flow, Double temperature, Double height){
        Measurement measurement = MeasurementConverter.fromJsonToMeasurement("{\"locationId\":\"" + locationId + "\",\"flow_ms\":" + flow + ",\"height\":" + height + ",\"temperature\":" + temperature + "}");

        assertEquals(locationId, measurement.getLocationId());
        assertEquals(flow, measurement.getFlow_ms());
        assertEquals(temperature, measurement.getTemperature());
        assertEquals(height, measurement.getHeight());
    }
}
