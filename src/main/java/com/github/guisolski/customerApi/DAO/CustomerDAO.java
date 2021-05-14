package com.github.guisolski.customerApi.DAO;

import com.github.guisolski.customerApi.Mapper.CustomerMapper;
import com.github.guisolski.customerApi.Model.Customer;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Timestamped;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface CustomerDAO extends DAO {
    @SqlQuery("SELECT * FROM customer WHERE id = :CUSTOMERID")
    @RegisterRowMapper(CustomerMapper.class)
    Customer findById(@Bind("CUSTOMERID") Integer CUSTOMERID);
    @SqlQuery("SELECT * FROM customer")
    @RegisterRowMapper(CustomerMapper.class)
    List<Customer> findAll();
    @SqlUpdate("INSERT INTO customer(NAME, EMAIL, CPF, GENDER, BIRTHDATE, CREATEAT, UPDATEAT) " +
            "VALUES(:name, :email,:cpf, :gender, :birthDate,:now,:now);")
    @Timestamped
    @GetGeneratedKeys
    Integer  create(@BindBean Customer customer);
    @SqlUpdate("UPDATE customer SET NAME = :name, EMAIL = :email, CPF = :cpf, GENDER = :gender," +
            " BIRTHDATE =:birthDate, UPDATEAT = :now" +
            " WHERE ID = :id")
    @Timestamped
    void update(@BindBean Customer customer);
    @SqlUpdate("DELETE FROM customer WHERE ID = :customer_id")
    void delete(@Bind("customer_id") Integer customer_id);
}
