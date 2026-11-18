package com.megamart.megamart.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.megamart.megamart.Entity.Customer;
import com.megamart.megamart.Entity.CustomerUpdateRequest;
import com.megamart.megamart.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CUSTOMER REGISTRATION
    public Customer registerCustomer(Customer customer) {

        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if (customerRepository.findByMobileNumber(customer.getMobileNumber()).isPresent()) {
            throw new RuntimeException("Mobile number already registered");
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerRepository.save(customer);
    }

    // CUSTOMER LOGIN
    public Customer loginCustomer(String identifier, String password) {

        Optional<Customer> customer;

        if (identifier.contains("@")) {
            customer = customerRepository.findByEmail(identifier);
        } else {
            customer = customerRepository.findByMobileNumber(identifier);
        }

        if (customer.isEmpty()) {
            throw new RuntimeException("Invalid email/mobile or password");
        }

        Customer existingCustomer = customer.get();

        if (!passwordEncoder.matches(password, existingCustomer.getPassword())) {
            throw new RuntimeException("Invalid email/mobile or password");
        }

        return existingCustomer;
    }

    // GET CUSTOMER PROFILE
    public Customer getCustomerProfile(Long id) {

        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // UPDATE CUSTOMER PROFILE
    public Customer updateCustomerProfile(Long id,
            CustomerUpdateRequest request) {

        Customer customer = getCustomerProfile(id);

        Optional<Customer> mobileOwner =
                customerRepository.findByMobileNumber(request.getMobileNumber());

        if (mobileOwner.isPresent()
                && !mobileOwner.get().getId().equals(id)) {
            throw new RuntimeException("Mobile number already registered");
        }

        customer.setFullName(request.getFullName());
        customer.setMobileNumber(request.getMobileNumber());
        customer.setAddress(request.getAddress());

        return customerRepository.save(customer);
    }
}