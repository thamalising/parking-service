package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Driver {
    @JsonProperty("lon")
    private double currentLongitude;

    @JsonProperty("lat")
    private double currentLatitude;

    @JsonProperty("radius")
    double radius;

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "[lat: "+currentLatitude + ", lon:"+currentLongitude + ", radius:"+radius +"]";
    }
}
