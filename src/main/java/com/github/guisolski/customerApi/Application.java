package com.github.guisolski.customerApi;

import com.github.guisolski.customerApi.Controller.AddressController;
import com.github.guisolski.customerApi.Controller.CustomerController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class Application {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppInjector());
        injector.getInstance(CustomerController.class);
        injector.getInstance(AddressController.class);
    }
}