package com.tmz.parkingservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="warden")
public class Warden {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

//    @OneToMany(mappedBy="id")
//    private List<Location> slots;
//
//    public List<Location> getSlots() {
//        return slots;
//    }
//
//    public void setSlots(List<Location> slots) {
//        this.slots = slots;
//    }

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
