package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="ParkingDetails")
public class ParkingDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonProperty("vehiNo")
    private String VehicleNo;

    @JsonProperty("vehiType")
    private String VehicleType;

    @JsonProperty("parkingStartTime")
    private String parkingStartTime;

    @JsonProperty("parkingEndTime")
    private String parkingEndTime;

    @ManyToOne(targetEntity = Warden.class)
    @JoinColumn(name="wid")
    private Warden warden;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getParkingStartTime() {
        return parkingStartTime;
    }

    public void setParkingStartTime(String parkingStartTime) {
        this.parkingStartTime = parkingStartTime;
    }

    public String getParkingEndTime() {
        return parkingEndTime;
    }

    public void setParkingEndTime(String parkingEndTime) {
        this.parkingEndTime = parkingEndTime;
    }

    public Warden getWarden() {
        return warden;
    }

    public void setWarden(Warden warden) {
        this.warden = warden;
    }

    @Override
    public String toString() {
        return  "[id:" + id +", vehiNo:"+ VehicleNo+", vehiType:" + VehicleType+", parkingStartTime:" + parkingStartTime+", parkingEndTime:" + parkingEndTime+", wid:" + warden + "]";
    }
}
