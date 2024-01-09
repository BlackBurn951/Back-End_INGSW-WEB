package com.example.progettowebtest.Connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConn {

    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource non configurato correttamente");
        }
        return dataSource.getConnection();
    }

    public static void setDataSource(DataSource dataSource) {
        DbConn.dataSource = dataSource;
    }

}
