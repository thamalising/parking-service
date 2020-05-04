package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="warden")
public class Warden {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nic")
    private String nic;

    @JsonProperty("mobile")
    private String mobileNO;

    @JsonProperty("address")
    private String address;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    private int responsibleLocations = 0;

    @JsonProperty("color")
    private String color = "#DAF7A6";

    private void updateColorCode()
    {
        if (responsibleLocations < 1) color = "#DAF7A6";
        else if (responsibleLocations == 1 || responsibleLocations == 2) color = "#FFC300";
        else if (responsibleLocations == 3 || responsibleLocations == 4) color = "#FF5733";
        else if (responsibleLocations > 4) color = "#900C3F";
    }

    public void increaseLocationCount() {
        ++responsibleLocations;
        updateColorCode();
    }

    public void decreaseLocationCount() {
        --responsibleLocations;
        updateColorCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  "[id:" + id +", name:"+ name+", nic:" + nic+", mobileno:" + mobileNO+", address:" + address+", email:" + email + "]";
    }
}
