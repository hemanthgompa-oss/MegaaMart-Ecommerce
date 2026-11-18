package com.megamart.megamart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.megamart.megamart.Entity.Customer;
import com.megamart.megamart.Entity.CustomerResponse;
import com.megamart.megamart.Entity.CustomerUpdateRequest;
import com.megamart.megamart.Entity.LoginRequest;
import com.megamart.megamart.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody Customer customer) {

        Customer saved = customerService.registerCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CustomerResponse(saved.getId(), saved.getFullName(),
                        saved.getEmail(), saved.getMobileNumber(),
                        saved.getAddress()));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<CustomerResponse> loginCustomer(
            @RequestBody LoginRequest request) {

        Customer customer = customerService.loginCustomer(
                request.getIdentifier(), request.getPassword());

        return ResponseEntity.ok(
                new CustomerResponse(customer.getId(),
                        customer.getFullName(), customer.getEmail(),
                        customer.getMobileNumber(), customer.getAddress()));
    }

    // GET PROFILE
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerProfile(
            @PathVariable Long id) {

        Customer customer = customerService.getCustomerProfile(id);

        return ResponseEntity.ok(
                new CustomerResponse(customer.getId(),
                        customer.getFullName(), customer.getEmail(),
                        customer.getMobileNumber(), customer.getAddress()));
    }

    // UPDATE PROFILE
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomerProfile(
            @PathVariable Long id,
            @RequestBody CustomerUpdateRequest request) {

        Customer customer = customerService.updateCustomerProfile(id, request);

        return ResponseEntity.ok(
                new CustomerResponse(customer.getId(),
                        customer.getFullName(), customer.getEmail(),
                        customer.getMobileNumber(), customer.getAddress()));
    }
}