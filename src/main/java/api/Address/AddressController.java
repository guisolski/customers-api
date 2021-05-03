package api.Address;

import api.Customer.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static api.util.JsonUntil.json;
import static api.util.JsonUntil.objectMapper;
import static spark.Spark.*;

@Singleton
public class AddressController {
    @Inject
    private final AddressPersistence addressService;

    public AddressController() {

        this.addressService = new AddressPersistence();
        path("/customers/:customerID/addresses", () -> {
            //get all addresses
            get("", (req, res) -> {
                int customerID = Integer.parseInt(req.params(":customerID"));
                LinkedHashMap<String,String> parameters = new LinkedHashMap<String, String>();
                ArrayList<Address> address = this.addressService.getAllAddresses(customerID,parameters);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, json());
            post("", (req, res) -> {
                try {
                    int customerID = Integer.parseInt(req.params(":customerID"));
                    Address address = objectMapper.readValue(req.body(), Address.class);
                    address.setCustomerID(customerID);
                    addressService.createAddress(address);
                    res.status(201);
                    return address;
                } catch (Exception e) {
                    e.printStackTrace();
                    res.status(400);
                    return "{code : 'create_address', description : 'Parameter errados'";
                }
            }, json());
            get("/:id",(req, res) -> {
                int customerID = Integer.parseInt(req.params(":customerID"));
                int id = Integer.parseInt(req.params(":id"));
                Address address = this.addressService.getAddress(customerID,id,null);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, json());
            put("/:id",(req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                Address address = objectMapper.readValue(req.body(), Address.class);
                address.setId(id);
                address = this.addressService.updateAddress(address);
                if (address != null) return address;
                res.status(204);
                return "Não foi encontrado nenhum endereço";
            }, json());
            delete("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                this.addressService.deleteAddress(id);
                res.status(204);
                return "Sucesso : Removido com sucesso";
            },json());
        });
    }
}
