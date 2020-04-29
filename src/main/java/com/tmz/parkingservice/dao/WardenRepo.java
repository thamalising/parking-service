package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface WardenRepo  extends JpaRepository<Warden, Integer> {

}
