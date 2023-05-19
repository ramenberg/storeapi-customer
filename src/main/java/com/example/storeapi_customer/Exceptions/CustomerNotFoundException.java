package com.example.storeapi_customer.Exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer " + id + ".");
    }
}

