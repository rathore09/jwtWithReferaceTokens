package com.jwtAuthentication.jwtAuthentication.controller;

import com.jwtAuthentication.jwtAuthentication.entity.Customer;
import com.jwtAuthentication.jwtAuthentication.entity.Role;
import com.jwtAuthentication.jwtAuthentication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService  customerService;

    @PostMapping("/add")
    public ResponseEntity<Customer>add(@RequestBody Customer customer){

        return ResponseEntity.ok(customerService.add(customer));
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(){
        return new ResponseEntity<>("testing controller", HttpStatus.OK);

    }
}
