package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Location;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LocationRepo extends JpaRepository<Location, Integer> {

}
