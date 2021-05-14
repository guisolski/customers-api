package com.github.guisolski.customerApi.Service;


import com.github.guisolski.customerApi.Model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses(int customerID);
    Address getAddress(int customerID,int id) ;
    Address getAddress(int customerID,boolean main);
    Address createAddress(Address customer) ;

    Address updateAddress(Address customer);

    boolean deleteAddress(int id) ;
}
