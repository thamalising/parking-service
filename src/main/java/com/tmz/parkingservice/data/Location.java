package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lname")
    private String locationName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("slots")
    private int numOfSlots;

    @JsonProperty("enable")
    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumOfSlots() {
        return numOfSlots;
    }

    public void setNumOfSlots(int numOfSlots) {
        this.numOfSlots = numOfSlots;
    }

    @Override
    public String toString() {
        return  "[id:" + id +", lon:"+ longitude+", lat:" + latitude+", lname:" + locationName+", city:" + city+", slots:" + numOfSlots + "]";
    }
}
