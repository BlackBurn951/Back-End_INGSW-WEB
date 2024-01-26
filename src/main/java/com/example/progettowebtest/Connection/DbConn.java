package com.example.progettowebtest.Connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConn {

    private static DataSource dataSource;
    private static Connection connection;


    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = dataSource.getConnection();
        }
        return connection;
    }


    public static void setDataSource(DataSource dataSource) {
        DbConn.dataSource = dataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
