package com.tmz.parkingservice.controller;

import com.tmz.parkingservice.dao.WardenRepo;
import com.tmz.parkingservice.data.Location;
import com.tmz.parkingservice.data.Warden;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WardenController {

    @Autowired
    WardenRepo wardenRepo;

    final static Logger logger = Logger.getLogger(WardenController.class);

    @PostMapping("/wardens")
    public Warden addWarden(@RequestBody Warden warden) {
        logger.info("addWarden: " + warden);
        wardenRepo.save(warden);
        logger.debug("addWarden: " + warden.toString());
        return warden;
    }

}
