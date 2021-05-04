package com.github.guisolski.customerApi.Customer;

import com.github.guisolski.customerApi.Address.Address;
import com.github.guisolski.customerApi.util.TimeStamp;
import com.google.inject.Inject;

import java.sql.Date;
import java.util.ArrayList;

public class Customer {
    private int id;
    private String cpf, name, email, gender;
    private Date birthDate;
    private ArrayList<Address> addresses;
    private Address mainAddress;
    @Inject
    private TimeStamp timeStamp = new TimeStamp();

    public Customer() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public Address getMainAddress() {
        return this.mainAddress;
    }

    public void setMainAddress(Address address) {
        this.mainAddress = address;
    }

    public TimeStamp getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(TimeStamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static String convertFilds(String field){
        switch (field){
            case "CUSTOMER_NAME":
                return "NAME";
            case "CUSTOMER_BIRTH_DATE":
                return "BIRTHDATE";
            case "CUSTOMER_CREATED_AT":
                return "CREATEDAT";
            default:
                return "";
        }
    }
}
