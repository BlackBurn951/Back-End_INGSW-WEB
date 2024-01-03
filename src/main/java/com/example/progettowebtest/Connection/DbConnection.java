package com.example.progettowebtest.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection con;
    private DbConnection() {}

    public static Connection getInstance() {
        if(con!=null) {
            try {
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "PassPostGres");
            }catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Eccezione nella conessione!!!");
            }
        }
        return con;
    }
}
