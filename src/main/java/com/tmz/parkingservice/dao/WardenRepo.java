package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Warden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardenRepo  extends JpaRepository<Warden, Integer> {
}
