package com.tmz.parkingservice;

import com.tmz.parkingservice.data.Location;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ParkingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingServiceApplication.class, args);
		System.out.println("######### Parking System ########");
//		ConfigurableApplicationContext context = SpringApplication.run(ParkingServiceApplication.class, args);
//		Location location = context.getBean(Location.class);
//		location.print();
	}
}
