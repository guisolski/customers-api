package com.github.guisolski.customerApi.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.guisolski.customerApi.Model.Address;
import com.github.guisolski.customerApi.Persistence.AddressPersistence;
import com.github.guisolski.customerApi.util.JsonUntil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import spark.Spark;

import java.util.List;

import static spark.Spark.*;

@Singleton
public class AddressController {
    @Inject
    private AddressPersistence addressService;

    public AddressController() {
        path("/customers/:customerID/addresses", () -> {
            //get all addresses
            Spark.get("", (req, res) -> {
                int customerID = Integer.parseInt(req.params(":customerID"));
                List<Address> address = this.addressService.getAllAddresses(customerID);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, JsonUntil.json());
            Spark.post("", (req, res) -> {
                try {
                    int customerID = Integer.parseInt(req.params(":customerID"));
                    Address address = JsonUntil.objectMapper.readValue(req.body(), Address.class);
                    address.setCustomerID(customerID);
                    addressService.createAddress(address);
                    res.status(201);
                    return address;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    res.status(400);
                    return "error : 'Failed to Create'";
                }
            }, JsonUntil.json());
            Spark.get("/:id",(req, res) -> {
                int customerID = Integer.parseInt(req.params(":customerID"));
                int id = Integer.parseInt(req.params(":id"));
                Address address = this.addressService.getAddress(customerID,id);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, JsonUntil.json());
            Spark.put("/:id",(req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                Address address = JsonUntil.objectMapper.readValue(req.body(), Address.class);
                address.setId(id);
                address = this.addressService.updateAddress(address);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, JsonUntil.json());
            Spark.delete("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                this.addressService.deleteAddress(id);
                res.status(204);
                return "Sucesso : Removido com sucesso";
            }, JsonUntil.json());
        });
    }
}
