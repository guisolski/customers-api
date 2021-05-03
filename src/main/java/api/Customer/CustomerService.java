package api.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Customer> getAllCustomers(Map<String, String[]> parameters) throws SQLException;

    Customer getCustomer(int id) throws SQLException;

    Customer createCustomer(Customer customer) throws SQLException;

    Customer updateCustomer(Customer customer) throws SQLException, IllegalAccessException;

    boolean deleteCustumer(int id) throws SQLException;
}
