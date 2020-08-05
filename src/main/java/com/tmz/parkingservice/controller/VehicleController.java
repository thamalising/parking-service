package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.VehicleRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Vehicle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class VehicleController {

//    @Autowired
//    LocationRepo locationRepo;

    @Autowired
    VehicleRepo vehicleRepo;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @CrossOrigin
    @PostMapping("/parkingdetails")
    public ResponseEntity<Vehicle> addVehicleDetails(@RequestBody Vehicle vehicle)
    {
        logger.info("addVehicle: " + vehicle.toString());
        vehicleRepo.save(vehicle);
        logger.debug("addVehicle: " + vehicle.toString());
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }

    @CrossOrigin
    @GetMapping("/parkingdetails")
    public ResponseEntity<List<Vehicle>> getParkingDetails() {
        List<Vehicle> vehicledet=vehicleRepo.findAll();
        return  ResponseEntity.status(HttpStatus.OK).body(vehicledet);
    }

    @CrossOrigin
    @GetMapping("/parkingdetails1")
    public ResponseEntity<Set<Vehicle>> getParkingDetailsByPlateNo(@RequestBody Vehicle vehicle)
    {
        Set<Vehicle> ret = new HashSet<>();
        if(!vehicle.getPlateNo().isEmpty()){
            List<Vehicle> v = vehicleRepo.findByPlateNo(vehicle.getPlateNo());
            ret.addAll(v);
        }

        if (!vehicle.getVehicleModel().isEmpty()) {
            List<Vehicle> v = vehicleRepo.findByVehicleModel(vehicle.getVehicleModel());
            ret.addAll(v);
        }

        if (!vehicle.getInTime().isEmpty()) {
            List<Vehicle> v = vehicleRepo.findByInTime(vehicle.getInTime());
            ret.addAll(v);
        }

        if (!vehicle.getOutTime().isEmpty()) {
            List<Vehicle> v = vehicleRepo.findByOutTime(vehicle.getOutTime());
            ret.addAll(v);
        }

        if (vehicle.getSlotno()>0) {
            List<Vehicle> v = vehicleRepo.findBySlotno(vehicle.getSlotno());
            ret.addAll(v);
        }

        if (vehicle.getFare()>0){
            List<Vehicle> v = vehicleRepo.findByFare(vehicle.getFare());
            ret.addAll(v);
        }

        return ResponseEntity.status(HttpStatus.OK).body(ret);

    }

    @CrossOrigin
    @PutMapping("/parkingdetails/{xx}")
    public ResponseEntity<Vehicle> updateVehicleDet(@PathVariable("xx") int id, @RequestBody Vehicle vehicle) {
        Vehicle vehi = vehicleRepo.findById(id).orElse(null);
        if (vehi == null) {
            logger.error("updateVehicle: cannot find xx:"+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vehi);
        }

        if (!vehicle.getPlateNo().isEmpty())
            vehi.setPlateNo(vehicle.getPlateNo());
        if(!vehicle.getVehicleModel().isEmpty())
            vehi.setVehicleModel(vehicle.getVehicleModel());
        if(!vehicle.getInTime().isEmpty())
            vehi.setInTime(vehicle.getInTime());
        if(!vehicle.getOutTime().isEmpty())
            vehi.setOutTime(vehicle.getOutTime());
        if(vehi.getSlotno()>0)
            vehi.setSlotno(vehicle.getSlotno());

        vehicleRepo.save(vehi);
        return ResponseEntity.status(HttpStatus.OK).body(vehi);
    }


}
