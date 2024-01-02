package com.example.progettowebtest.DAO;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;
import java.sql.*;

public class UtenteDAO implements DbDAO{
    @Override
    public Vector<Objects> doRetriveAll() throws SQLException {
        return null;
    }

    @Override
    public Objects doRetriveByKey(Objects id) throws SQLException {
        return null;
    }

    @Override
    public void saveOrUpdate(Objects obj) throws SQLException {

    }

    @Override
    public void delete(Objects obj) throws SQLException {

    }
}
