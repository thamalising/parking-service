package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location, Integer> {
    List<Location> findByLocationName(String name);
}
