package com.example.storeapi_customer.Repos;

import com.example.storeapi_customer.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findBySsn(String ssn);
}
