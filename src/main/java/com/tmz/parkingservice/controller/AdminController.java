package com.tmz.parkingservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.dao.TokenRepo;
import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.AdminUser;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Token;
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
    @Autowired
    TokenRepo tokenRepo;
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

        // remove related location from warden
        Warden w = l.getWarden();
        if (w != null) {
            w.setResponsibleLocations(w.getResponsibleLocations() - 1);
            w.updateColorCode();
            wardenRepo.save(w);
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
    @PutMapping("/locations/detach")
    public ResponseEntity<String> disableSlot(@RequestParam("location-id") int id){
        Location l = locationRepo.findById(id).orElse(null);
        if (l == null) {
            logger.error("disableSlot failed, id:" + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        logger.info("disableSlot success, id:" + id);
        Warden w = l.getWarden();
        if (w != null) {
            w.setResponsibleLocations(w.getResponsibleLocations() - 1);
            w.updateColorCode();
            wardenRepo.save(w);
        }
        l.setWarden(null);
        locationRepo.save(l);
        return ResponseEntity.status(HttpStatus.OK).body("location disabled:"+id);
    }

    @CrossOrigin
    @PostMapping("/wardens")
    public ResponseEntity <Warden> addWarden(@RequestBody Warden warden) {
        logger.info("addWarden: " + warden);
        wardenRepo.save(warden);
        logger.debug("addWarden: " + warden.toString());
        return ResponseEntity.status(HttpStatus.OK).body(warden);
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
        // check token for deletion
        Token t = tokenRepo.findByWardenId(id).orElse(null);
        if (t != null) {
            tokenRepo.deleteById(t.getId());
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

        logger.info("updateWarden: warden id:"+ id);
        return ResponseEntity.status(HttpStatus.OK).body(w);
    }

    @CrossOrigin
    @PutMapping("/assign")
    public synchronized ResponseEntity<String> assignSlots(@RequestParam("warden-id") int xx, @RequestBody Location location){
       Location l = locationRepo.findById(location.getId()).orElse(null);
       if(l == null) {
           logger.error("assignSlots: no lname or id ");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no lname or id");
       }

        Warden w=wardenRepo.findById(xx).orElse(null);
        if(w == null) {
            logger.error("assignSlots: cannot find warden "+xx);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found"+xx);
        }
        w.setResponsibleLocations(w.getResponsibleLocations() + 1);
        w.updateColorCode();
        wardenRepo.save(w);

        l.setWarden(w);
        locationRepo.save(l);

        logger.info("assignSlots: warden:"+ xx + " assigned to location:"+ l.getId());
        return ResponseEntity.status(HttpStatus.OK).body("set warden:"+xx +"to location:"+l.getId());
    }

    //remove location/s from warden
    @CrossOrigin
    @PutMapping("/detach/{xx}")
    public ResponseEntity<String> detachWarden(@PathVariable("xx") int id) {
        Warden w = wardenRepo.findById(id).orElse(null);
        if (w==null) {
            logger.error("detachWarden: cannot find " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found " + id);
        }
        //remove relavant warden for related locations
        List<Location> locations = locationRepo.findByWarden(w);
        for (int i = 0; i < locations.size(); ++i) {
            Location l = locations.get(i);
            l.setWarden(null);
            locationRepo.save(l);
        }

        w.setResponsibleLocations(0);
        w.updateColorCode();
        wardenRepo.save(w);
        logger.info("detachWarden: detached: "+id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted " + id);
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminUser admin) {
        boolean success = admin.getUsername().equalsIgnoreCase("admin")
                && admin.getPassword().equalsIgnoreCase("admin");
        logger.info("login: "+success );
        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("login success");
    }

    @CrossOrigin
    @PostMapping("/token/{xx}")
    public ResponseEntity<String> generateToken(@PathVariable("xx") int wid){

        Token t = tokenRepo.findByWardenId(wid).orElse(null);
        if (t == null) {
            t = new Token();
            t.setWardenId(wid);
        }
        // setToken internally
        String value = t.generateToken();

        tokenRepo.save(t);
        return ResponseEntity.status(HttpStatus.OK).body(value);
    }

    @CrossOrigin
    @GetMapping("/tokens")
    public ResponseEntity<List<Token>> getTokens() {
        List<Token> ts = tokenRepo.findAll();
        logger.info("getTokens: ---" + ts.size());
        return ResponseEntity.status(HttpStatus.OK).body(ts);
    }

}