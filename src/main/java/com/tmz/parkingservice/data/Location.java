package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="location")
public class Location {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
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

    @ManyToOne(targetEntity = Warden.class)
    @JoinColumn(name="wid")
    private Warden warden;

    @JsonIgnore
    private int busyNumOfSlots = 0;

    public int getBusyNumOfSlots() {
        return busyNumOfSlots;
    }

    public void setBusyNumOfSlots(int busyNumOfSlots) {
        this.busyNumOfSlots = busyNumOfSlots;
    }

    public Warden getWarden() {
        return warden;
    }

    public void setWarden(Warden warden) {
        this.warden = warden;
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
