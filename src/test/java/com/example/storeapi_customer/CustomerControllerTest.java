package com.example.storeapi_customer;

import com.example.storeapi_customer.Models.Customer;
import com.example.storeapi_customer.Repos.CustomerRepo;
import com.example.storeapi_customer.Controllers.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;
    @MockBean
    private CustomerRepo customerMockRepo;

    @BeforeEach
    void setUp() {
        Customer c1 = new Customer("John", "Doe", "1234");
        Customer c2 = new Customer("Jane", "Done", "0987");
        when(customerMockRepo.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(customerMockRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(customerMockRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(customerMockRepo.findById(3L)).thenReturn(Optional.empty());
        when(customerMockRepo.findBySsn("1234")).thenReturn(c1);
        when(customerMockRepo.findBySsn("0987")).thenReturn(c2);
        when(customerMockRepo.findBySsn("0000")).thenReturn(null);
    }

    @Test
    void allTestSizeShouldBeEqual() {
        List<Customer> customers = customerController.all();
        assertEquals(2, customers.size());
    }

    @Test
    void allTestFirstNameShouldBeEqual() {
        List<Customer> customers = customerController.all();
        assertEquals("John", customers.get(0).getFirstName());
    }

    @Test
    void one() throws Exception {
        Customer customer = customerController.one(2L);
        assertEquals("Jane", customer.getFirstName());
    }

    @Test
    void customerWithSsnAlreadyExistsTest() {
        assertTrue(customerController.customerWithSsnAlreadyExists("1234"));
    }

    @Test
    void customerDoesNotHaveAllRequiredFieldsTest() {
        Customer c = new Customer("Inge", "Ring", null);
        assertTrue(customerController.customerDoesNotHaveAllRequiredFields(c));
    }

    @Test
    void saveNewCustomerTest() {
        Customer c = new Customer("Inge", "Ring", "0000");
        Customer customer = customerController.saveNewCustomer(c);
        assertEquals("Inge", customer.getFirstName());
    }
}