package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LocationRepo;
import com.tmz.parkingservice.data.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {
    @Autowired
    LocationRepo repo;

    @PostMapping("/location")
    public Location addLocation(@RequestBody Location location)
    {
        System.out.println( "add location called ");
        repo.save(location);
        return location;
    }

    @GetMapping("/locations")
    public List<Location> getLocations()
    {
        System.out.println("getting....  all locations");
        return repo.findAll();
    }

    @GetMapping("/locations/{xx}") // what ever comming from {my-id} assigned below id- pathvariable meant 2h 4m
    public Optional<Location> getLocation(@PathVariable("xx") int id)
    {
        System.out.println("getting.... location:" + id);
        return repo.findById(id);
    }

    @PutMapping("/location")
    public Location saveOrUpdate(@RequestParam("xx") int xx,  @RequestBody Location location)
    {
        System.out.println("updating.... location:[" + xx + "]"+ location);

        Location l = repo.findById(xx).orElse(null);
        l.setLatitude(location.getLatitude());
        repo.save(l);

        return location;
    }

    @PutMapping("/location-ex")
    public Location saveOrUpdate(@RequestBody Location location)
    {
        System.out.println("updating.... location:"+ location);
        repo.save(location);
        return location;
    }

    @DeleteMapping("/location-del/{my-id}")
    public String delete(@PathVariable("my-id") int id)
    {
        System.out.println("deleting.... location:" + id);
        repo.deleteById(id);
        return id + " deleted";
    }
}
