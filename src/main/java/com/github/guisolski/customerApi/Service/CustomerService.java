package com.github.guisolski.customerApi.Service;


import com.github.guisolski.customerApi.Model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers(Customer parameters);
    Customer getCustomer(int id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    boolean deleteCustumer(int id);
}
