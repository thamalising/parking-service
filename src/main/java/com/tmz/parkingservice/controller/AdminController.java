package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

    @RequestMapping(value = "/locations",method = RequestMethod.POST)
    public Location addLocation(@RequestBody Location location) {
        logger.info("addLocation: " + location.toString());
        locationRepo.save(location);
        logger.debug("addLocation: " + location.toString());
        return location;
    }

    @RequestMapping(value = "/locations",method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> ret = locationRepo.findAll();
        logger.debug("getLocations: " + ret);
        logger.info("getLocations: success");
        return new ResponseEntity<>(ret,HttpStatus.OK);
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

    @RequestMapping(value = "/wardens/{xx}", method = RequestMethod.DELETE)
    public String deleteWarden(@PathVariable("xx") int id) {
        wardenRepo.deleteById(id);
        return id + " deleted";
    }

    @RequestMapping(value = "/assign", method = RequestMethod.PUT)
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
