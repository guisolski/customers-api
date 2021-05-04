package com.github.guisolski.customerApi.Address;

import com.github.guisolski.customerApi.util.JsonUntil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import spark.Spark;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static spark.Spark.*;

@Singleton
public class AddressController {
    @Inject
    private final AddressPersistence addressService;

    public AddressController() {

        this.addressService = new AddressPersistence();
        path("/customers/:customerID/addresses", () -> {
            //get all addresses
            Spark.get("", (req, res) -> {
                int customerID = Integer.parseInt(req.params(":customerID"));
                LinkedHashMap<String,String> parameters = new LinkedHashMap<String, String>();
                ArrayList<Address> address = this.addressService.getAllAddresses(customerID,parameters);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, JsonUntil.json());
            Spark.post("", (req, res) -> {
                try {
                    int customerID = Integer.parseInt(req.params(":customerID"));
                    Address address = JsonUntil.objectMapper.readValue(req.body(), Address.class);
                    address.setCustomerID(customerID);
                    this.addressService.createAddress(address);
                    res.status(201);
                    return address;
                } catch (Exception e) {
                    e.printStackTrace();
                    res.status(400);
                    return "{code : 'create_address', description : 'Parameter errados'";
                }
            }, JsonUntil.json());
            Spark.get("/:id",(req, res) -> {
                int customerID = Integer.parseInt(req.params(":customerID"));
                int id = Integer.parseInt(req.params(":id"));
                Address address = this.addressService.getAddress(customerID,id,null);
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
