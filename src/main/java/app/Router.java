package app;

import static spark.Spark.*;
import static spark.Spark.delete;

public class Router {
    public void routes(){
        path("/customer",()->{
            //get all customers
            get("",(req, res) -> "Hello World" );
            //add new customer
            post("",(req, res) -> "Hello Tete" );
            //get customer by id
            get("/:id",(req, res) -> "Hello Tete" );
            //update customer by id
            put("/:id",(req, res) -> "Hello Tete" );
            //delete customer by id
            delete("/:id",(req, res) -> "Hello Tete" );
                path("/:id/adresses", () ->{
                //get all addresses from customer
                get("",(req, res) -> "Hello World" );
                //add new addresses to customer
                post("",(req, res) -> "Hello Tete" );
                //get one addresse from custemer by id
                get("/:id",(req, res) -> "Hello Tete" );
                //update addresse from custemer by id
                put("/:id",(req, res) -> "Hello Tete" );
                //delete addresse from custemer by id
                delete("/:id",(req, res) -> "Hello Tete" );
            });
        });
    }
}
