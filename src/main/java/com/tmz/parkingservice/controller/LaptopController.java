package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.LaptopRepo;
import com.tmz.parkingservice.data.Laptop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    @Autowired
    LaptopRepo repo;

    @PostMapping("/laptop")
    public Laptop addLaptop(@RequestBody Laptop laptop)
    {
        System.out.println("adding.... laptop: " + laptop);
        repo.save(laptop);
        return laptop;
    }

    @GetMapping("/laptops")
    public List<Laptop> getLaptops()
    {
        System.out.println("getting.... laptops");
        return repo.findAll();
    }

    @GetMapping("/laptops/{my-id}") // what ever comming from {my-id} assigned below id- pathvariable meant 2h 4m
    public Optional<Laptop> getLaptop(@PathVariable("my-id") int id)
    {
        System.out.println("getting.... laptop:" + id);
        return repo.findById(id);
    }

    @DeleteMapping("/laptops/{my-id}")
    public String delete(@PathVariable("my-id") int id)
    {
        System.out.println("deleting.... laptop:" + id);
        Laptop laptop = repo.getOne(id);

        repo.deleteById(id);

        return id + " deleted";
    }

    @PutMapping("/laptop")
    public Laptop saveOrUpdate(@RequestBody Laptop laptop)
    {
        System.out.println("updating.... laptop:" + laptop);

        repo.save(laptop);

        return laptop;
    }
}
