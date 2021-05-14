package com.github.guisolski.customerApi.Mapper;

import com.github.guisolski.customerApi.Model.Address;

import com.github.guisolski.customerApi.util.TimeStamp;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AddressMapper implements RowMapper<Address> {

    @Override
    public Address map(ResultSet rs, StatementContext ctx) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("ID"));
        address.setCity(rs.getString("CITY"));
        address.setState(rs.getString("STATE"));
        address.setNeighborhood(rs.getString("NEIGHBORHOOD"));
        address.setStreet(rs.getString("STREET"));
        address.setAdditionalInformation(rs.getString("ADDITIONALINFORMATION"));
        address.setNumber(rs.getInt("NUMBER"));
        address.setZipCode(rs.getString("ZIPCODE"));
        address.setMain(rs.getBoolean("MAIN"));
        address.setCustomerID(rs.getInt("CUSTOMERID"));
        address.setTimeStamp(new TimeStamp(rs.getDate("CREATEAT"),
                rs.getDate("UPDATEAT")));
        return address;
    }

    @Override
    public RowMapper<Address> specialize(ResultSet rs, StatementContext ctx) throws SQLException {
        return RowMapper.super.specialize(rs, ctx);
    }

    @Override
    public void init(ConfigRegistry registry) {
        RowMapper.super.init(registry);
    }
}
