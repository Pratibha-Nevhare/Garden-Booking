package com.garden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages= {"com.garden.controller"})
public class GardenBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GardenBookingApplication.class, args);
		System.out.println("hello");
	}

}

