package com.github.guisolski.customerApi;

import com.github.guisolski.customerApi.Address.AddressController;
import com.github.guisolski.customerApi.Customer.CustomerController;
import com.google.inject.Singleton;

@Singleton
public class Application {
    public static void main(String[] args) {
        new CustomerController();
        new AddressController();
    }
}