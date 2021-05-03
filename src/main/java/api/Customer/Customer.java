package api.Customer;

import api.Address.Address;
import api.util.TimeStamp;
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
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public Address getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address address) {
        this.mainAddress = address;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
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