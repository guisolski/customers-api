package api;

import api.Address.AddressController;
import api.Customer.CustomerController;
import com.google.inject.Singleton;

@Singleton
public class Application {
    public static void main(String[] args) {
        new CustomerController();
        new AddressController();
    }
}