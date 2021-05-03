package api.Address;


import api.Customer.Customer;
import api.Persistence;
import api.util.TimeStamp;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AddressPersistence extends Persistence implements AddressService {
    @Override
    public ArrayList<Address> getAllAddresses(int customerID, Map<String, String> parameters) throws SQLException {
        String query = "SELECT * FROM address WHERE CUSTOMERID = ? ";
        for(String key : parameters.keySet()){
            query += " AND "+key+" = " + parameters.get(key);
        }
        this.preparementGet(query);
        ArrayList<Address> addresses = new ArrayList<>();
        preparedStatement.setInt(1, customerID);
        this.get();
        while (result.next()) {
            addresses.add(getAddress(result));
        }
        close();
        return addresses;
    }

    @Override
    public Address getAddress(int customerID, int id, Boolean  main) throws SQLException {
        String query = "SELECT * FROM address WHERE CUSTOMERID = ? AND ID = ?";
        Address addresses = null;
        if(main != null){
            query += " AND MAIN = "+ main;
        }
        this.preparementGet(query);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setInt(2, id);

        this.get();
        if (result.next()) {
            addresses = getAddress(result);
            close();
            return addresses;
        }else {
            close();
            return null;
        }


    }
    @Override
    public Address getMainAddres(int customerID, Boolean  main) throws SQLException {
        String query = "SELECT * FROM ADDRESS WHERE CUSTOMERID = ? AND MAIN = ?";
        Address address = null;

        this.preparementGet(query);
        preparedStatement.setInt(1, customerID);
        preparedStatement.setBoolean(2, main);
        this.get();
        while (result.next()) {
            address = getAddress(result);
        }
        close();
        return address;
    }

    @Override
    public Address createAddress(Address address) throws SQLException {
        String query = "INSERT INTO ADDRESS (CUSTOMERID, STATE, CITY, NEIGHBORHOOD, STREET, ADDITIONALINFORMATION," +
                " ZIPCODE, NUMBER, MAIN, CREATEAT, UPDATEAT) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?) ";

        this.preparementUpdate(query);
        preparedStatement.setInt(1, address.getCustomerID());
        preparedStatement.setString(2, address.getState());
        preparedStatement.setString(3, address.getCity());
        preparedStatement.setString(4, address.getNeighborhood());
        preparedStatement.setString(5, address.getStreet());
        preparedStatement.setString(6, address.getAdditionalInformation());
        preparedStatement.setString(7, address.getZipCode());
        preparedStatement.setInt(8, address.getNumber());
        preparedStatement.setBoolean(9, address.getMain());
        preparedStatement.setDate(10, address.getTimeStamp().getCreatedAt());
        preparedStatement.setDate(11, address.getTimeStamp().getUpdateAt());
        this.executeUpdate();
        if (result.next()) {
            address.setId(result.getInt(1));
        }
        close();
        return address;
    }

    @Override
    public Address updateAddress(Address address) throws IllegalAccessException, SQLException {
        String query = "UPDATE ADDRESS SET ";
        Field[] fields = Address.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object objeto = field.get(address);
            if (objeto != null) {
                String name = field.getName().toUpperCase();
                if (!name.equals("CUSTOMERID") && !name.equals("ID")) {
                    if (name.equals("TIMESTAMP"))
                        query += "UPDATEAT = '" + address.getTimeStamp().getUpdateAt() + "'";
                    else if (name.equals("MAIN"))
                        query += name.toUpperCase() + " = " + objeto + " ,";
                    else
                        query += name.toUpperCase() + " = '" + objeto + "', ";
                }
            }
        }
        query += " WHERE ID = ?";
        this.preparementUpdate(query);
        this.preparedStatement.setInt(1, address.getId());
        this.preparedStatement.execute();
        close();
        return address;
    }

    @Override
    public boolean deleteAddress(int id) throws SQLException {
        String query = "Delete FROM ADDRESS WHERE ID = ?";
        this.preparementUpdate(query);
        this.preparedStatement.setInt(1, id);
        return this.preparedStatement.execute();
    }
    public int getIdMainAddress(int customerID) throws SQLException {
        String query = "SELECT ID FROM ADDRESS WHERE CUSTOMERID = ? AND MAIN = TRUE";
        this.preparementGet(query);
        this.preparedStatement.setInt(1, customerID);
        this.get();
        if (this.result.next()) {
            return this.result.getInt("ID");
        }
        return 0;
    }

    private Address getAddress(ResultSet result) throws SQLException {
        Address address = new Address();
        address.setId(result.getInt("ID"));
        address.setCity(result.getString("CITY"));
        address.setState(result.getString("STATE"));
        address.setNeighborhood(result.getString("NEIGHBORHOOD"));
        address.setStreet(result.getString("STREET"));
        address.setAdditionalInformation(result.getString("ADDITIONALINFORMATION"));
        address.setNumber(result.getInt("NUMBER"));
        address.setZipCode(result.getString("ZIPCODE"));
        address.setMain(result.getBoolean("MAIN"));
        address.setCustomerID(result.getInt("CUSTOMERID"));
        address.setTimeStamp(new TimeStamp(result.getDate("CREATEAT"),
                result.getDate("UPDATEAT")));
        return address;
    }
}
