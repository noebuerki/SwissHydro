package ch.bbcag.swisshydro.repository.database.objects;

public class Measurement {

    private final String locationId;
    private Double flow_ms;
    private Double flow_ls;
    private Double height;
    private Double depth;
    private Double temperature;

    public Measurement(String locationId) {
        this.locationId = locationId;
    }

    public void setProperty(String par, double val) {
        switch (par) {
            case "flow":
                flow_ms = val;
                break;
            case "flow_ls":
                flow_ls = val;
                break;
            case "height":
                height = val;
                break;
            case "height_abs":
                depth = val;
                break;
            case "temperature":
                temperature = val;
                break;
        }
    }

    public String getLocationId() {
        return locationId;
    }

    public Double getFlow_ms() {
        return flow_ms;
    }

    public Double getFlow_ls() {
        return flow_ls;
    }

    public Double getHeight() {
        return height;
    }

    public Double getDepth() {
        return depth;
    }

    public Double getTemperature() {
        return temperature;
    }
}
