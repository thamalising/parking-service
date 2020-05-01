package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Driver;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {
    @Autowired
    LocationRepo locationRepo;
    final static Logger logger = Logger.getLogger(DriverController.class);

    @GetMapping("/parking-locations")
    public ResponseEntity<List<Location>> getParkingLocations(@RequestBody Driver driver) {

        List<Location> locations = locationRepo.findAll();
        logger.info("getParkingLocations: get matched parking locations for driver:" + driver.toString());
        return ResponseEntity.status(HttpStatus.OK).body(locations);
    }
}
