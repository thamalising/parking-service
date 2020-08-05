package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Vehicle;
import com.tmz.parkingservice.data.Warden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByPlateNo(String no);
    List<Vehicle> findByVehicleModel(String mod);
    List<Vehicle> findByInTime(String in);
    List<Vehicle> findByOutTime(String out);
    List<Vehicle> findBySlotno(int slot);
    List<Vehicle> findByFare(double fare);
}
