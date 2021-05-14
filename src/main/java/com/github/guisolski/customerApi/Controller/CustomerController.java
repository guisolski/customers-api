package com.github.guisolski.customerApi.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.guisolski.customerApi.Model.Customer;
import com.github.guisolski.customerApi.Persistence.CustomerPersistence;
import com.github.guisolski.customerApi.util.JsonUntil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import spark.Spark;

import java.util.List;

import static spark.Spark.*;

@Singleton
public class CustomerController {
    @Inject
    private CustomerPersistence customerService;

    public CustomerController() {
        path("/customers", () -> {
            //get all customers
            Spark.get("", (req, res) -> {
                //req.queryMap().toMap();
                List<Customer> customers =  customerService.getAllCustomers(new Customer());
                return  customers;
            }, JsonUntil.json());
            //add new customer
            Spark.post("", (req, res) -> {
                try {
                    String body = req.body().replace("address","mainAddress");
                    Customer customer = JsonUntil.objectMapper.readValue(body, Customer.class);
                    customerService.createCustomer(customer);
                    res.status(201);
                    return customer;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    res.status(400);
                    return "error : 'Failed to Create'";
                }
            }, JsonUntil.json());
            //get customer by id
            Spark.get("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                Customer customer = customerService.getCustomer(id);
                if (customer != null) return customer;
                res.status(400);
                return "Consumidor com ID:" + id + "não encontrado";
            }, JsonUntil.json());
            //update customer by id
            Spark.put("/:id", (req, res) -> {
                try {
                    int id = Integer.parseInt(req.params(":id"));
                    String body = req.body().replace("address","mainAddress");
                    Customer customer = JsonUntil.objectMapper.readValue(body, Customer.class);
                    customer.setId(id);
                    customer = customerService.updateCustomer(customer);
                    return customer;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    res.status(400);
                    return "error : 'Failed to Update'";
                }
            }, JsonUntil.json());
            //delete customer by id
            Spark.delete("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                customerService.deleteCustumer(id);
                res.status(204);
                return "Sucesso : Removido com sucesso";
            }, JsonUntil.json());
        });
    }
}
