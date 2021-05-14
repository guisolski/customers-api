package com.github.guisolski.customerApi.Persistence;


import com.github.guisolski.customerApi.DAO.AddressDAO;
import com.github.guisolski.customerApi.Model.Address;
import com.github.guisolski.customerApi.Service.AddressService;

import javax.inject.Singleton;
import java.sql.ResultSet;
import java.util.List;
@Singleton
public class AddressPersistence extends Persistence implements AddressService {
    @Override
    public List<Address> getAllAddresses(int customerID)   {
        List<Address> addresses = handle.attach(AddressDAO.class).findAll(customerID);
        return addresses;
    }

    public List<Address> getAllAddresses(Address parameters)   {
        List<Address> addresses = handle.attach(AddressDAO.class).findAll(parameters);
        return addresses;
    }

    @Override
    public Address getAddress(int customerID, int id){
        Address address = handle.attach(AddressDAO.class).findById(customerID, id);
        return address;
    }

    @Override
    public Address getAddress(int customerID, boolean main) {
        Address address = handle.attach(AddressDAO.class).findMainAddress(customerID, main);
        return address;
    }

    @Override
    public Address createAddress(Address address){
        try {
            address.setId(handle.attach(AddressDAO.class).create(address));
            //customer.setMainAddress(addressService.createAddress(customer.getMainAddress()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return address;
        }
    }

    @Override
    public Address updateAddress(Address address){
        try {
           handle.attach(AddressDAO.class).update(address);
            //customer.setMainAddress(addressService.createAddress(customer.getMainAddress()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return address;
        }
    }

    @Override
    public boolean deleteAddress(int id){
        try {
            handle.attach(AddressDAO.class).delete(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public int getIdMainAddress(int customerID){
        return 1;
    }

    private Address getAddress(ResultSet result){
        return null;
    }
}