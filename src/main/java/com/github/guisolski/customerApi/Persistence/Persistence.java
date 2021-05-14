package com.github.guisolski.customerApi.Persistence;


import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.sql.DataSource;

public class Persistence  {
    protected static String url, username, password;
    protected static Handle handle;
    protected Jdbi jdbi;
    public Persistence() {
        url = "jdbc:mysql://localhost:3306/customers";
        username = "root";
        password = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        jdbi = Jdbi.create(url,username,password);
        jdbi.installPlugin(new SqlObjectPlugin());
        handle = jdbi.open();
    }
}
