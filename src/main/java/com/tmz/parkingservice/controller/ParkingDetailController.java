package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.ParkingDetailsRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.ParkingDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingDetailController {
    @Autowired
    WardenRepo wardenRepo;
    @Autowired
    ParkingDetailsRepo parkingDetailsRepo;
    final static Logger logger = Logger.getLogger(AdminController.class);

    @CrossOrigin
    @PostMapping("/parkingdetails")
    public ResponseEntity<ParkingDetails> addParkingDetails(@RequestBody ParkingDetails parkingDetails){
        parkingDetailsRepo.save(parkingDetails);
        return ResponseEntity.status(HttpStatus.OK).body(parkingDetails);
    }

}
