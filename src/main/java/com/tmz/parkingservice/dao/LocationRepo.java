package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location, Integer> {
    List<Location> findByLocationName(String name);
    List<Location> findByWarden(Warden w);
}
