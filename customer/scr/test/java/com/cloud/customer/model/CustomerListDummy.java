package com.cloud.customer.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerListDummy {


    public static List<Customer> getCustomers(){
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customers.add(customer);
        }
        return customers;
    }

}
