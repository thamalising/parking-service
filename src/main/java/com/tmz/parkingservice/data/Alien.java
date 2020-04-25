package com.tmz.parkingservice.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Alien {
    private int id;
    private String name;
//    @Autowired
//    @Qualifier("lp")
//    private Laptop laptop;

    public Alien() {
        System.out.println("Alien ctr called");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show()
    {
        System.out.println("id: " + id);
        System.out.println("name: " + name);
    }
}
