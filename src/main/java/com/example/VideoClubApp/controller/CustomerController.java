package com.example.VideoClubApp.controller;

import com.example.VideoClubApp.model.Customer;
import com.example.VideoClubApp.model.dto.CustomerRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.VideoClubApp.service.CustomerService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})

@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService= customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id){
        System.out.println("Customer has been found");
        return new ResponseEntity<>(customerService.findCustomerById(id),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerRequestDto customer){
        final Customer createdCustomer = customerService.insertCustomer(customer);
        System.out.println("Customer Created");
        return new ResponseEntity<>(createdCustomer,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public boolean deleteCustomers(){
        customerService.deleteAllCustomers();
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCustomerById(@PathVariable("id") Long id){
        customerService.deleteCustomerById(id);
        System.out.println("Customer with id: %s"+id+"have been deleted");
        return true;
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@RequestBody CustomerRequestDto customer, @PathVariable("id") Long id){
        System.out.println("Customer updated successfully");
        return customerService.updateCustomer(customer,id);
    }


}
