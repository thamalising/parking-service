package com.tmz.parkingservice;

import com.tmz.parkingservice.data.Alien;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ParkingServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ParkingServiceApplication.class, args);

		Alien a = context.getBean(Alien.class);
		System.out.println("thamali");
		a.show();
	}

}
