package com.github.guisolski.customerApi.Mapper;

import com.github.guisolski.customerApi.Model.Customer;
import com.github.guisolski.customerApi.util.TimeStamp;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer map(ResultSet rs, StatementContext ctx) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("ID"));
        customer.setName(rs.getString("NAME"));
        customer.setEmail(rs.getString("EMAIL"));
        customer.setCpf(rs.getString("CPF"));
        customer.setBirthDate(rs.getDate("BIRTHDATE"));
        customer.setTimeStamp(new TimeStamp(rs.getDate("CREATEAT"),
                rs.getDate("UPDATEAT")));

        return customer;
    }

    @Override
    public RowMapper<Customer> specialize(ResultSet rs, StatementContext ctx) throws SQLException {
        return RowMapper.super.specialize(rs, ctx);
    }

    @Override
    public void init(ConfigRegistry registry) {
        RowMapper.super.init(registry);
    }
}
