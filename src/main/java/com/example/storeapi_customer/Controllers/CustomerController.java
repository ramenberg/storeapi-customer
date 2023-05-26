package com.example.storeapi_customer.Controllers;

import com.example.storeapi_customer.Models.Customer;
import com.example.storeapi_customer.Repos.CustomerRepo;
import com.example.storeapi_customer.Exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final Logger log = Logger.getLogger(CustomerController.class.getName());
    private final CustomerRepo customerRepo;
    private final RestTemplate restTemplate;

    public CustomerController(CustomerRepo customerRepo, RestTemplate restTemplate) {
        this.customerRepo = customerRepo;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public List<Customer> all() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public Customer newCustomer(@RequestBody Customer newCustomer) {
        // Kontrollera att alla fält är ifyllda
        if (customerDoesNotHaveAllRequiredFields(newCustomer)) {
            log.info("Invalid customer: " + newCustomer + ".");
            return newCustomer;
            // Kontrollera att kund med ssn(unikt) inte redan finns
        } else if (customerWithSsnAlreadyExists(newCustomer.getSsn())) {
            log.info("Customer with ssn " + newCustomer.getSsn() + " already exists.");
            return customerRepo.findBySsn(newCustomer.getSsn());
        } else {
            return saveNewCustomer(newCustomer);
        }
    }

    public Boolean customerWithSsnAlreadyExists(String ssn) {
        return customerRepo.findBySsn(ssn) != null;
    }
    public Boolean customerDoesNotHaveAllRequiredFields(Customer customer) {
        return customer.getFirstName() == null ||
                customer.getLastName() == null ||
                customer.getSsn() == null;

    }
    public Customer saveNewCustomer(Customer customer) {
        log.info("Successfully created new customer: " + customer + ".");
        customerRepo.save(customer);
        return customer;
    }
}
