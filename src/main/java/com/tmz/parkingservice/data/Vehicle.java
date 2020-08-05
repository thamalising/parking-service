package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonProperty("plateno")
    private String plateNo;

    @JsonProperty("vehiclemodel")
    private String vehicleModel;

    @JsonProperty("intime")
    private String inTime;

    @JsonProperty("outtime")
    private String outTime;

    @JsonProperty("slotno")
    private int slotno;

    @JsonProperty("fare")
    private double fare;

    public int getId() {
        return id;
    }

//    @ManyToOne(targetEntity = Location.class)
//    @JoinColumn(name="lid")
    @JsonProperty("lid")
    private int location;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public int getSlotno() {
        return slotno;
    }

    public void setSlotno(int slotno) {
        this.slotno = slotno;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plateNo='" + plateNo + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", slotno=" + slotno +
                ", fare=" + fare +
                '}';
    }
}
