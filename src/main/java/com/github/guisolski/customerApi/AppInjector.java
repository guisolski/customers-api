package com.github.guisolski.customerApi;

import com.github.guisolski.customerApi.Model.Address;
import com.github.guisolski.customerApi.Model.Customer;
import com.github.guisolski.customerApi.Persistence.AddressPersistence;
import com.github.guisolski.customerApi.Persistence.CustomerPersistence;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class AppInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(Customer.class).annotatedWith(Names.named("Customer")).toInstance(new Customer());
        bind(Address.class).annotatedWith(Names.named("Address")).toInstance(new Address());
        bind(AddressPersistence.class).annotatedWith(Names.named("AddressPersistence"))
                .toInstance(new AddressPersistence());
        bind(CustomerPersistence.class).annotatedWith(Names.named("CustomerPersistence"))
                .toInstance(new CustomerPersistence());
    }
}
