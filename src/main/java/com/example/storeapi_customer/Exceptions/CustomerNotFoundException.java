package com.example.storeapi_customer.Exceptions;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer with id " + id + ".");
    }
}

