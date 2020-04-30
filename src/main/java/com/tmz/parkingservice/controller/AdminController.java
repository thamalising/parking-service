package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Test;
import com.tmz.parkingservice.data.Warden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
public class AdminController {
    @Autowired
    LocationRepo locationRepo;
    @Autowired
    WardenRepo wardenRepo;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @PostMapping("/test")
    public ResponseEntity<Test> test(@RequestBody Test test) {
        logger.info("test: " + test.toString());
        return ResponseEntity.status(HttpStatus.OK).body(test);
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        logger.info("addLocation: " + location.toString());
        locationRepo.save(location);
        logger.debug("addLocation: " + location.toString());
        return ResponseEntity.status(HttpStatus.OK).body(location);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> ret = locationRepo.findAll();
        logger.debug("getLocations: " + ret);
        logger.info("getLocations: success");
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @GetMapping("/locations/{xx}")
    public ResponseEntity<Location> getLocation(@PathVariable("xx") int id) {
        Location location = locationRepo.findById(id).orElse(null);
        if (location == null) {
            logger.error("getLocation: failed id:" + id );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(location);
        }
        logger.info("getLocation: success id:" + id);
        logger.debug("getLocation: success id:" + id + " location:" + location.toString());
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PutMapping("/location")
    public ResponseEntity<Location> updateLocation(@RequestParam("xx") int xx, @RequestBody Location location) {
        Location l = locationRepo.findById(xx).orElse(null);
        if (l == null) {
            logger.error("updateLocation: cannot find xx:"+xx);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(location);
        }
        if (!location.getLocationName().isEmpty())
            l.setLocationName(location.getLocationName());
        if(location.getLatitude() > 0)
            l.setLatitude(location.getLatitude());
        if(location.getLongitude()>0)
            l.setLongitude(location.getLongitude());
        if(location.getCity().isEmpty())
            l.setCity(location.getCity());
        if(location.getNumOfSlots()>0)
            l.setNumOfSlots(location.getNumOfSlots());
        l.setWarden(null);
        locationRepo.save(l);
        logger.info("updateLocation: updated success xx:"+xx+ " location:"+l.toString());
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

    @DeleteMapping("/location/{xx}")
    public ResponseEntity<String> deleteLocation(@PathVariable("xx") int id) {
        Location l = locationRepo.findById(id).orElse(null);
        if (l==null){
            logger.error("deleteLocation:  cannot find:"+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cannot find: "+id);
        }

        locationRepo.deleteById(id);
        logger.info("deleteLocation: deleted: "+ id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted: "+ id);
    }

    @DeleteMapping("locations")
    public void deleteAll()
    {
        locationRepo.deleteAll();
        logger.info("deleteAll: success");
    }

    @PutMapping("/slot-enable")
    public ResponseEntity<String> enableSlot(@RequestParam("xx") int xx, @RequestBody Location location){
        Location l = locationRepo.findById(xx).orElse(null);
        if (l == null) {
            logger.error("enableSlot failed, id:" + xx + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        logger.info("enableSlot success, id:" + xx + " enable:" + location.isEnable());
        l.setEnable(location.isEnable());
        locationRepo.save(l);
        return ResponseEntity.status(HttpStatus.OK).body("location enable:"+location.isEnable());
    }

    @GetMapping("/wardens")
    public ResponseEntity<List<Warden>> getWardens() {
        List<Warden> ret = wardenRepo.findAll();
        logger.info("getWardens: success");
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @GetMapping("/wardens/{xx}")
    public ResponseEntity<Warden> getWarden(@PathVariable("xx") int id) {
        Warden warden = wardenRepo.findById(id).orElse(null);
        if (warden == null) {
            logger.error("getWarden: failed id:" + id );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("getWarden: success id:" + id);
        logger.debug("getWarden: success id:" + id + " warden:" + warden.toString());
        return ResponseEntity.status(HttpStatus.OK).body(warden);
    }

    @DeleteMapping("/wardens/{xx}")
    public ResponseEntity<String> deleteWarden(@PathVariable("xx") int id) {
        Warden warden = wardenRepo.findById(id).orElse(null);
        if (warden==null) {
            logger.error("deleteWarden: cannot find " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found " + id);
        }
        wardenRepo.deleteById(id);
        logger.info("deleteWarden: deleted: "+id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted " + id);
    }

    @PutMapping("/assign")
    public ResponseEntity<String> assignSlots(@RequestParam("warden-id") int xx, @RequestBody Location location){
        // check whether warden is valid
        Warden w = wardenRepo.findById(xx).orElse(null);
        if (w == null) {
            // no warden found fo xx id
            logger.error("assign: id:" + xx + " not found");
            return new ResponseEntity<>("warden not found", HttpStatus.NOT_FOUND);
        }
        logger.info("assign: id:" + xx + " warden found");
        // priority for the id, location is checked by id
        Location l = locationRepo.findById(location.getId()).orElse(null);
        if (l != null) {
            // location found for id xx
            l.setWarden(w);
            locationRepo.save(l);
            return new ResponseEntity<>("assign by id " +xx+" success", HttpStatus.OK);
        }

        // check location name is valid?
        if (location.getLocationName().isEmpty()) {
            logger.error("assign: name not found");
            return new ResponseEntity<>("name not found", HttpStatus.NOT_FOUND);
        }

        // get all locations
        List<Location> ls=locationRepo.findAll();
        for (Location l2:ls){
            if (location.getLocationName().equalsIgnoreCase(l2.getLocationName())) {
                l2.setWarden(w);
                locationRepo.save(l2);
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
