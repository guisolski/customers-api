package api.Customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static api.util.JsonUntil.json;
import static api.util.JsonUntil.objectMapper;
import static spark.Spark.*;

@Singleton
public class CustomerController {
    @Inject
    private static final CustomerPersistence customerService = new CustomerPersistence();

    public CustomerController() {
        path("/customers", () -> {
            //get all customers
            get("", (req, res) -> {
                ArrayList<Customer> customers =  customerService.getAllCustomers(req.queryMap().toMap());
                return  customers;
            }, json());
            //add new customer
            post("", (req, res) -> {
                try {
                    String body = req.body().replace("address","mainAddress");
                    Customer customer = objectMapper.readValue(body, Customer.class);
                    customerService.createCustomer(customer);
                    res.status(201);
                    return customer;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    res.status(400);
                    return "error : 'Failed to Create'";
                }
            }, json());
            //get customer by id
            get("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                Customer customer = customerService.getCustomer(id);
                if (customer != null) return customer;
                res.status(400);
                return "Consumidor com ID:" + id + "não encontrado";
            },json());
            //update customer by id
            put("/:id", (req, res) -> {
                try {
                    int id = Integer.parseInt(req.params(":id"));
                    String body = req.body().replace("address","mainAddress");
                    Customer customer = objectMapper.readValue(body, Customer.class);
                    customer.setId(id);
                    customer = customerService.updateCustomer(customer);
                    return customer;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    res.status(400);
                    return "error : 'Failed to Update'";
                }
            }, json());
            //delete customer by id
            delete("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                customerService.deleteCustumer(id);
                res.status(204);
                return "Sucesso : Removido com sucesso";
            },json());
        });
    }
}
