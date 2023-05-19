package com.example.storeapi_customer.Controllers;

import com.example.storeapi_customer.Models.Customer;
import com.example.storeapi_customer.Repos.CustomerRepo;
import com.example.storeapi_customer.Exceptions.CustomerNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class CustomerController {

    private final Logger log = Logger.getLogger(CustomerController.class.getName());
    private final CustomerRepo customerRepo;

    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    @GetMapping("/customers")
    public List<Customer> all() {
        return customerRepo.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer one(@PathVariable Long id) throws CustomerNotFoundException {
        log.info("GET /customers/" + id);
        return customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping("/customers")
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
