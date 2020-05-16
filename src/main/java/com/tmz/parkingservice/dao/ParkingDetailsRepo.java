package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.ParkingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingDetailsRepo extends JpaRepository<ParkingDetails,Integer> {
}
