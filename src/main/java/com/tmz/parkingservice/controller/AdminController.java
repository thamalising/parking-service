package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.data.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@RestController
public class AdminController {
    @Autowired
    LocationRepo repo;
    final static Logger logger = Logger.getLogger(AdminController.class);

    @PostMapping("/location")
    public Location addLocation(@RequestBody Location location) {
        repo.save(location);
        return location;
    }

    @GetMapping("/locations")
    public List<Location> getLocations() {
        logger.error("thamliiiiiiiii");
        return repo.findAll();
    }

    @GetMapping("/locations/{xx}")
    public Optional<Location> getLocation(@PathVariable("xx") int id) {
        return repo.findById(id);
    }

    @PutMapping("/location")
    public Location saveOrUpdate(@RequestParam("xx") int xx, @RequestBody Location location) {
        Location l = repo.findById(xx).orElse(null);
        l.setLatitude(location.getLatitude());
        repo.save(l);
        return location;
    }

    @PutMapping("/location-ex")
    public Location saveOrUpdate(@RequestBody Location location) {
        repo.save(location);
        return location;
    }

    @DeleteMapping("/location/{xx}")
    public String delete(@PathVariable("xx") int id) {
        repo.deleteById(id);
        return id + " deleted";
    }
    @DeleteMapping("locations")
    public void deleteAll()
    {
        repo.deleteAll();
    }
}
