package api.Address;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Address> getAllAddresses(int customerID, Map<String, String> parameters) throws SQLException;

    Address getAddress(int customerID,int id, Boolean main) throws SQLException;
    Address getMainAddres(int customerID, Boolean main) throws SQLException;
    Address createAddress(Address customer) throws SQLException;

    Address updateAddress(Address customer) throws SQLException, IllegalAccessException;

    boolean deleteAddress(int id) throws SQLException;
}
