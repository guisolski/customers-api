package com.github.guisolski.customerApi.Persistence;

import com.github.guisolski.customerApi.DAO.CustomerDAO;
import com.github.guisolski.customerApi.Model.Customer;
import com.github.guisolski.customerApi.Service.CustomerService;
import com.google.inject.Inject;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class CustomerPersistence extends Persistence implements CustomerService {
    @Inject
    private AddressPersistence addressService;
    @Override
    public List<Customer> getAllCustomers(Customer parameters){
        List<Customer> customers= handle.attach(CustomerDAO.class).findAll();
        for(Customer customer : customers){
            customer.setMainAddress(addressService.getAddress(customer.getId(), true));
        }
        return  customers;
    }

    public Customer getCustomer(int id){
        Customer customer= handle.attach(CustomerDAO.class).findById(id);
        customer.setMainAddress(addressService.getAddress(customer.getId(), true));
        return customer;
    }

    public Customer createCustomer(Customer customer){
        try {
            customer.setId(handle.attach(CustomerDAO.class).create(customer));
            //customer.setMainAddress(addressService.createAddress(customer.getMainAddress()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return customer;
        }
    }

    public Customer updateCustomer(Customer customer){
        try {
            handle.attach(CustomerDAO.class).update(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return customer;
        }
    }
    public boolean deleteCustumer(int id){
        try {
            handle.attach(CustomerDAO.class).delete(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
