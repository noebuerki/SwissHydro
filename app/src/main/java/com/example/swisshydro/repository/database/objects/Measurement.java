package com.example.swisshydro.repository.database.objects;

public class Measurement {

    private String locationId;
    private Long timestamp;
    private Double flow;
    private Double flow_ls;
    private Double height;
    private Double depth;
    private Double temperature;


    public void setProperty(String par, double val) {
        switch (par) {
            case "flow":
                flow = val;
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

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    public Double getFlow_ls() {
        return flow_ls;
    }

    public void setFlow_ls(Double flow_ls) {
        this.flow_ls = flow_ls;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
