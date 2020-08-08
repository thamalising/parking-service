package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WardenController {
    @Autowired
    LocationRepo locationRepo;
    @Autowired
    WardenRepo wardenRepo;
    final static Logger logger = Logger.getLogger(WardenController.class);

    @CrossOrigin
    @GetMapping("/my-locations/{xx}")
    public ResponseEntity<List<Location>> getAssignedLocations(@PathVariable("xx") int id) {
        Warden w = wardenRepo.findById(id).orElse(null);
        if (w ==  null) {
            logger.error("getAssignedLocations: cannot find id:" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Location> locations = locationRepo.findByWarden(w);
        logger.info("getAssignedLocations: locations:" + locations.size() + " has assigned for warden:"+ id);
        return ResponseEntity.status(HttpStatus.OK).body(locations);
    }

    @CrossOrigin
    @PutMapping("/availability/{op}")
    public ResponseEntity<Integer> updateAvailability(@RequestParam("location-id") int id, @PathVariable("op") String op) {
        Location l = locationRepo.findById(id).orElse(null);
        if (l ==  null) {
            logger.error("updateAvailability: cannot find id:" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }

        if (!(op.equalsIgnoreCase("plus") || op.equalsIgnoreCase("minus"))) {
            logger.error("updateAvailability: invalid operator:" + op);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
        }

        int allSlots = l.getNumOfSlots();
        int bcSlots = l.getBusyNumOfSlots();
        if (op.equalsIgnoreCase("plus")) {
            if (++bcSlots > allSlots) {
                bcSlots = allSlots;
            }
        } else if (op.equalsIgnoreCase("minus")) {
            if (--bcSlots < 0) {
                bcSlots = 0;
            }
        }

        l.setBusyNumOfSlots(bcSlots);
        locationRepo.save(l);

        logger.info("updateAvailability: location:" + id + " has "+ bcSlots + " locations");
        return ResponseEntity.status(HttpStatus.OK).body(bcSlots);
    }

    @CrossOrigin
    @GetMapping("/ami/{warden-id}")
    public ResponseEntity<Warden> ami(@PathVariable("warden-id") int id) {

        Warden w = wardenRepo.findById(id).orElse(null);
        if (w == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(w);
    }
}
