package com.github.guisolski.customerApi.DAO;

import com.github.guisolski.customerApi.Mapper.AddressMapper;
import com.github.guisolski.customerApi.Model.Address;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Timestamped;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


import java.util.List;

public interface AddressDAO extends DAO {
    @SqlUpdate("INSERT INTO address(CUSTOMERID, STATE, CITY, NEIGHBORHOOD, STREET, ADDITIONALINFORMATION, ZIPCODE," +
            " NUMBER, MAIN, CREATEAT, UPDATEAT)" +
            " VALUES(:customerID, :state, :city, :neighborhood, :street, :additionalInformation, :zipCode," +
            "  :number, :main, :now, :now)")
    @Timestamped
    @GetGeneratedKeys
    Integer create(@BindBean Address address);

    @SqlQuery("SELECT * FROM address WHERE CUSTOMERID = :CUSTOMERID")
    @RegisterRowMapper(AddressMapper.class)
    List<Address> findAll(@Bind("CUSTOMERID") Integer CUSTOMERID);

    @SqlQuery("SELECT * FROM address WHERE CUSTOMERID = :customerID, STATE = :state, CITY = :city, " +
            "NEIGHBORHOOD = :neighborhood, STREET = :street,ADDITIONALINFORMATION = :additionalInformation, " +
            " ZIPCODE = :zipCode, NUMBER = :number, MAIN = :main;")
    @RegisterRowMapper(AddressMapper.class)
    List<Address> findAll(@BindBean Address address);

    @SqlQuery("SELECT * FROM address WHERE CUSTOMERID = :CUSTOMERID AND MAIN = :MAIN LIMIT 1")
    @RegisterRowMapper(AddressMapper.class)
    Address findMainAddress(@Bind("CUSTOMERID") Integer CUSTOMERID, @Bind("MAIN") boolean MAIN);

    @SqlQuery("SELECT * FROM address WHERE CUSTOMERID = :CUSTOMERID AND ID = :ID LIMIT 1")
    @RegisterRowMapper(AddressMapper.class)
    Address findById(@Bind("CUSTOMERID") Integer CUSTOMERID, @Bind("ID") Integer ID);

    @SqlUpdate("UPDATE address SET STATE = :state, CITY = :city,  NEIGHBORHOOD = :neighborhood, ZIPCODE = :zipCode," +
            " NUMBER =:number, ADDITIONALINFORMATION = :additionalInformation, MAIN = :main, UPDATEAT = :now " +
            " WHERE ID = :id")
    @Timestamped
    void update(@BindBean Address address);
    @SqlUpdate("DELETE FROM address WHERE ID = :customer_id")
    void delete(@Bind("customer_id") Integer customer_id);
}
