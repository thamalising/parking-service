package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@RestController
public class AdminController {
    @Autowired
    LocationRepo locationRepo;
    @Autowired
    WardenRepo wardenRepo;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @PostMapping("/locations")
    public Location addLocation(@RequestBody Location location) {
        logger.info("addLocation: " + location);
        locationRepo.save(location);
        logger.debug("addLocation: " + location.toString());
        return location;
    }

    @GetMapping("/locations")
    public List<Location> getLocations() {
        List<Location> ret = locationRepo.findAll();
        logger.debug("getLocations: " + ret);
        logger.info("getLocations: success");
        return ret;
    }

    @GetMapping("/locations/{xx}")
    public Optional<Location> getLocation(@PathVariable("xx") int id) {
        Optional<Location> location = locationRepo.findById(id);
        if (location == null) {
            logger.error("getLocation: failed id:" + id );
            return null;
        }
        logger.info("getLocation: success id:" + id);
        logger.debug("getLocation: success id:" + id + " location:" + location.toString());
        return location;
    }

    @PutMapping("/location")
    public Location saveOrUpdate(@RequestParam("xx") int xx, @RequestBody Location location) {
        Location l = locationRepo.findById(xx).orElse(null);
        l.setLatitude(location.getLatitude());
        locationRepo.save(l);
        return location;
    }

    @PutMapping("/location-ex")
    public Location saveOrUpdate(@RequestBody Location location) {
        locationRepo.save(location);
        return location;
    }

    @DeleteMapping("/location/{xx}")
    public String delete(@PathVariable("xx") int id) {
        locationRepo.deleteById(id);
        return id + " deleted";
    }
    @DeleteMapping("locations")
    public void deleteAll()
    {
        locationRepo.deleteAll();
    }

    @PutMapping("/slot-enable")
    public Boolean enableSlot(@RequestParam("xx") int xx, @RequestBody Location location){
        Location l = locationRepo.findById(xx).orElse(null);
        if (l == null) {
            logger.error("enableSlot failed, id:" + xx + " not found");
            return !location.isEnable();
        }
        logger.info("enableSlot success, id:" + xx + " enable:" + location.isEnable());
        l.setEnable(location.isEnable());
        locationRepo.save(l);
        return location.isEnable();

    }

    @GetMapping("/wardens")
    public List<Warden> getWardens() {
        List<Warden> ret = wardenRepo.findAll();
        logger.debug("getWardens: " + ret);
        logger.info("getWardens: success");
        return ret;
    }

    @GetMapping("/wardens/{xx}")
    public Optional<Warden> getWarden(@PathVariable("xx") int id) {
        Optional<Warden> warden = wardenRepo.findById(id);
        if (warden == null) {
            logger.error("getWarden: failed id:" + id );
            return null;
        }
        logger.info("getWarden: success id:" + id);
        logger.debug("getWarden: success id:" + id + " warden:" + warden.toString());
        return warden;
    }

    @DeleteMapping("/wardens/{xx}")
    public String deleteWarden(@PathVariable("xx") int id) {
        wardenRepo.deleteById(id);
        return id + " deleted";
    }
}
