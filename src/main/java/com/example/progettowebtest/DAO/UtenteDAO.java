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

        try {
            PreparedStatement statement;
            String query= "insert into utente (cf, nome, cognome, cittadinanza, comune_di_nascita, sesso, provincia_di_nascita, num_telefono, data_di_nascita, " +
                    "email, password, num_identificativo_ci, num_patente, num_passaporto, nome_via_domicilio, num_civico_domicilio, nome_via_residenza, " +
                    "num_civico_residenza, id_comune_residenza, id_comune_domicilio, id_via_residenza, id_via_domicilio, occupazione, reditto_annuo) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }
    }

    @Override
    public void delete(Objects obj) throws SQLException {

    }
}
