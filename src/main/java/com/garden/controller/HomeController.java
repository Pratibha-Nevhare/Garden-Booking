package com.garden.controller;



import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.garden.dao.GCustomerRepository;
import com.garden.entities.G_customer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    private final GCustomerRepository gCustomerRepository;

    public HomeController(GCustomerRepository gCustomerRepository) {
        this.gCustomerRepository = gCustomerRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home"; 
    }

    @GetMapping("/booking")
    public String showBookingForm() {
        return "booking"; 
    }

    @GetMapping("/service")
    public String showServices() {
        return "services"; 
    }
    
    @GetMapping("/about")
    public String about() {
        return "about"; 
    }
    
    @GetMapping("/submitBooking")
    public String submitBooking(
            HttpServletRequest request,
            @RequestParam int B_ID,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam int noOfPerson,
            @RequestParam int price,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date creationDate,  ServletResponse response) {
        G_customer customer = new G_customer();
        customer.setB_ID(B_ID);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setNo_Of_Person(noOfPerson);
        customer.setPrice(price);
        customer.setCreationDate(creationDate);

        gCustomerRepository.save(customer);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/");

        try {
           
            dispatcher.forward(request, response);
        } catch (Exception e) {
         
            e.printStackTrace();
        }

        return "";
    }
}
