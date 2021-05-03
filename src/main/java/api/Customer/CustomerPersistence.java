package api.Customer;

import api.Address.Address;
import api.Address.AddressPersistence;
import api.Persistence;
import api.util.TimeStamp;
import com.google.inject.Inject;
import org.apache.logging.log4j.core.util.ArrayUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomerPersistence extends Persistence implements CustomerService {
    @Inject
    private static final AddressPersistence addressService = new AddressPersistence();
    public CustomerPersistence() {
        super();
    }

    public ArrayList<Customer> getAllCustomers(Map<String, String[]> parameters) throws SQLException {
        String query = "SELECT * FROM CUSTOMER";
        ArrayList<Customer> customers = new ArrayList<>();
        int count = 0;
        for(String key : parameters.keySet()){
            if(key.equals("sortBy") == false && key.equals("sortOrder") == false){
                if(count > 0)   query += " AND "+key.toUpperCase()+" = " + parameters.get(key)[0];
                else    query += " WHERE "+key.toUpperCase()+" = " + parameters.get(key)[0];
            }
            ++count;
        }
        if(parameters.containsKey("sortBy")){
            query += " ORDER BY "+ Customer.convertFilds(parameters.get("sortBy")[0]);
            query += " " + parameters.get("sortOrder")[0].toUpperCase();
        }
        this.preparementGet(query);
        this.get();
        while (result.next()) {
            customers.add(getCustomer(result));
        }
        close();
        for(Customer customer : customers){
            this.setAddresses(customer);
        }
        return customers;
    }

    public Customer getCustomer(int id) throws SQLException {
        String query = "SELECT * FROM CUSTOMER WHERE ID = ?";
        Customer customer = null;

        this.preparementGet(query);
        preparedStatement.setInt(1, id);
        this.get();
        while (result.next()) {
                customer = getCustomer(result);
        }
        close();
        this.setAddresses(customer);
        return customer;

    }

    public Customer createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO CUSTOMER (NAME, EMAIL, CPF, GENDER, BIRTHDATE, CREATEAT, UPDATEAT) " +
                "VALUES(?,?,?,?,?,?,?) ";

        this.preparementUpdate(query);
        //add values on query
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getEmail());
        preparedStatement.setString(3, customer.getCpf());
        preparedStatement.setString(4, customer.getGender());
        preparedStatement.setDate(5, customer.getBirthDate());
        preparedStatement.setDate(6, customer.getTimeStamp().getCreatedAt());
        preparedStatement.setDate(7, customer.getTimeStamp().getUpdateAt());
        //execute query
        this.executeUpdate();
        if (result.next()) {
            customer.setId(result.getInt(1));
            customer.getMainAddress().setCustomerID(customer.getId());
            customer.setMainAddress(addressService.createAddress(customer.getMainAddress()));
        }
        close();
        return customer;
    }

    public Customer updateCustomer(Customer customer) throws IllegalAccessException, SQLException {
        String query = "UPDATE CUSTOMER SET ";
        Field[] fields = Customer.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object objeto = field.get(customer);
            if(objeto != null){
                String name = field.getName().toUpperCase();
                if(!name.equals("MAINADDRESS") && !name.equals("ADDRESSES") && !name.equals("ID")){
                    if(name.equals("TIMESTAMP"))
                        query += "UPDATEAT = '" + customer.getTimeStamp().getUpdateAt()+"'";
                    else
                        query += name.toUpperCase() + " = '" + objeto +"', ";
                }
            }
        }
        query += " WHERE ID = ?";

        this.preparementUpdate(query);
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.execute();
        close();
        if(customer.getAddresses() != null)
            //Addresses necessary have Id inplace
            for(Address address : customer.getAddresses())
                address = addressService.updateAddress(address);
        if(customer.getMainAddress() != null){
            //get ID to MainAddress
            customer.getMainAddress().setId(addressService.getIdMainAddress(customer.getId()));
            //Update MainAddress
            customer.setMainAddress(addressService.updateAddress(customer.getMainAddress()));
        }
        return customer;
    }
    public boolean deleteCustumer(int id) throws SQLException {
        String query = "DELETE FROM CUSTOMER WHERE ID = ?";
        this.preparementUpdate(query);
        preparedStatement.setInt(1, id);
        return preparedStatement.execute();
    }

    private Customer getCustomer(ResultSet result) throws SQLException {
        Customer customer = new Customer();
        customer.setId(result.getInt("ID"));
        customer.setName(result.getString("NAME"));
        customer.setEmail(result.getString("EMAIL"));
        customer.setCpf(result.getString("CPF"));
        customer.setBirthDate(result.getDate("BIRTHDATE"));
        customer.setTimeStamp(new TimeStamp(result.getDate("CREATEAT"),
                result.getDate("UPDATEAT")));
        return customer;
    }
    private void setAddresses(Customer customer) throws SQLException {
        Map<String,String> parameters = new LinkedHashMap<>();
        parameters.put("MAIN","FALSE");
        customer.setAddresses(addressService.getAllAddresses(customer.getId(),parameters));
        customer.setMainAddress(addressService.getMainAddres(customer.getId(), true));
    }
}
