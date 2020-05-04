package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.log4j.Logger;

@RestController
public class AdminController {
    @Autowired
    LocationRepo locationRepo;
    @Autowired
    WardenRepo wardenRepo;
    final static Logger logger = Logger.getLogger(AdminController.class);

    @CrossOrigin
    @PostMapping("/locations")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        logger.info("addLocation: " + location.toString());
        locationRepo.save(location);
        logger.debug("addLocation: " + location.toString());
        return ResponseEntity.status(HttpStatus.OK).body(location);
    }

    @CrossOrigin
    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> ret = locationRepo.findAll();
        logger.debug("getLocations: " + ret);
        logger.info("getLocations: success");
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @CrossOrigin
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

    @CrossOrigin
    @PutMapping("/locations/{xx}")
    public ResponseEntity<Location> updateLocation(@PathVariable("xx") int id, @RequestBody Location location) {
        Location l = locationRepo.findById(id).orElse(null);
        if (l == null) {
            logger.error("updateLocation: cannot find xx:"+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(location);
        }
        if (!location.getLocationName().isEmpty())
            l.setLocationName(location.getLocationName());
        if(location.getLatitude() > 0)
            l.setLatitude(location.getLatitude());
        if(location.getLongitude()>0)
            l.setLongitude(location.getLongitude());
        if(!location.getCity().isEmpty())
            l.setCity(location.getCity());
        if(location.getNumOfSlots()>0)
            l.setNumOfSlots(location.getNumOfSlots());
        l.setWarden(null);
        locationRepo.save(l);
        logger.info("updateLocation: updated success xx:"+id+ " location:"+l.toString());
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

    @CrossOrigin
    @DeleteMapping("/locations/{xx}")
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

    @CrossOrigin
    @DeleteMapping("locations")
    public void deleteAll()
    {
        locationRepo.deleteAll();
        logger.info("deleteAll: success");
    }

    // suspend locations temporarory
    @CrossOrigin
    @PutMapping("/locations-enable")
    public ResponseEntity<String> enableSlot(@RequestParam("id") int id, @RequestParam("enable") boolean value){
        Location l = locationRepo.findById(id).orElse(null);
        if (l == null) {
            logger.error("enableSlot failed, id:" + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        logger.info("enableSlot success, id:" + id + " enable:" + value);
        l.setEnable(value);
        locationRepo.save(l);
        return ResponseEntity.status(HttpStatus.OK).body("location enable:"+value);
    }

    @CrossOrigin
    @PostMapping("/wardens")
    public Warden addWarden(@RequestBody Warden warden) {
        logger.info("addWarden: " + warden);
        wardenRepo.save(warden);
        logger.debug("addWarden: " + warden.toString());
        return warden;
    }

    @CrossOrigin
    @GetMapping("/wardens")
    public ResponseEntity<List<Warden>> getWardens() {
        List<Warden> ret = wardenRepo.findAll();
        logger.info("getWardens: success");
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @CrossOrigin
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

    @CrossOrigin
    @DeleteMapping("/wardens/{xx}")
    public ResponseEntity<String> deleteWarden(@PathVariable("xx") int id) {
        Warden warden = wardenRepo.findById(id).orElse(null);
        if (warden==null) {
            logger.error("deleteWarden: cannot find " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found " + id);
        }
        //remove relavant warden for related locations
        List<Location> locations = locationRepo.findByWarden(warden);
        for (int i = 0; i < locations.size(); ++i) {
            Location l = locations.get(i);
            l.setWarden(null);
            locationRepo.save(l);
        }

        wardenRepo.deleteById(id);
        logger.info("deleteWarden: deleted: "+id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted " + id);
    }

    @CrossOrigin
    @DeleteMapping("/wardens")
    public ResponseEntity<String> deleteAllWardens() {
        //remove relavant warden for related locations
        List<Location> locations = locationRepo.findAll();
        for (int i = 0; i < locations.size(); ++i) {
            Location l = locations.get(i);
            l.setWarden(null);
            locationRepo.save(l);
        }

        wardenRepo.deleteAll();
        logger.info("deleteWarden: all deleted:");
        return ResponseEntity.status(HttpStatus.OK).body("deleted all");
    }

    @CrossOrigin
    @PutMapping("/wardens/{xx}")
    public ResponseEntity<Warden> updateWarden(@PathVariable("xx") int id, @RequestBody Warden warden) {
        Warden w =wardenRepo.findById(id).orElse(null);
        if (w == null) {
            logger.error("updateWarden: cannot find warden id:"+ id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (!warden.getAddress().isEmpty()) {
            w.setAddress(warden.getAddress());
        }
        if (!warden.getEmail().isEmpty()) {
            w.setEmail(warden.getEmail());
        }
        if (!warden.getMobileNO().isEmpty()) {
            w.setMobileNO(warden.getMobileNO());
        }
        if (!warden.getName().isEmpty()) {
            w.setName(warden.getName());
        }
        if (!warden.getNic().isEmpty()) {
            w.setNic(warden.getNic());
        }
        wardenRepo.save(w);

        logger.error("updateWarden: cannot find warden id:"+ id);
        return ResponseEntity.status(HttpStatus.OK).body(w);
    }

    @CrossOrigin
    @PutMapping("/assign")
    public ResponseEntity<String> assignSlots(@RequestParam("warden-id") int xx, @RequestBody Location location){
       Warden w=wardenRepo.findById(xx).orElse(null);
       if(w == null) {
           logger.error("assignSlots: cannot find warden "+xx);
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found"+xx);
       }

       Location l = locationRepo.findById(location.getId()).orElse(null);
       if(l == null) {
           if (!location.getLocationName().isEmpty()) {
               List<Location> locations =locationRepo.findByLocationName(location.getLocationName());
                for(int i=0;i<locations.size();i++) {
                    Location l2 = locations.get(i);
                    l2.setWarden(w);
                    w.increaseLocationCount();
                    locationRepo.save(l2);
                }
                if (locations.size() > 0)
                    return ResponseEntity.status(HttpStatus.OK).body("set warden for " + locations.size() + " locations");
           }
           logger.error("assignSlots: no lname or id ");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no lname or id");
       }
       l.setWarden(w);
        w.increaseLocationCount();
       locationRepo.save(l);
        logger.error("assignSlots: warden:"+ xx + " assigned to location:"+ l.getId());
       return ResponseEntity.status(HttpStatus.OK).body("set warden:"+xx +"to location:"+l.getId());
    }

    // detach warden from all locations
    @CrossOrigin
    @PutMapping("/detach/{xx}")
    public ResponseEntity<String> detachWarden(@PathVariable("xx") int id) {
        Warden warden = wardenRepo.findById(id).orElse(null);
        if (warden==null) {
            logger.error("detachWarden: cannot find " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found " + id);
        }
        //remove relavant warden for related locations
        List<Location> locations = locationRepo.findByWarden(warden);
        for (int i = 0; i < locations.size(); ++i) {
            Location l = locations.get(i);
            l.setWarden(null);
            warden.decreaseLocationCount();
            locationRepo.save(l);
        }

        logger.info("detachWarden: detached: "+id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted " + id);
    }
}
