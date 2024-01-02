package com.example.progettowebtest.DAO;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;

public interface DbDAO {
    Vector<Objects> doRetriveAll() throws SQLException;
    Objects doRetriveByKey(Objects id) throws SQLException;
    void saveOrUpdate(Objects obj) throws SQLException;
    void delete(Objects obj) throws SQLException;
}
