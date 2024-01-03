package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.Model.*;

import java.sql.SQLException;
import java.util.Vector;
import java.sql.*;

public class UtenteDAOImpl implements UtenteDAO {

    @Override
    public Vector<Utente> doRetriveAll(){
        return null;
    }

    @Override
    public Utente doRetriveByKey(String cf){
        return null;
    }

    @Override
    public boolean saveOrUpdate(Utente ut) {
        Indirizzo res, dom;
        DocumentiIdentita doc;
        boolean result= true;
        try {
            //chimata al doRetriveByKey per capire se fare un update o una insert (funzione private all'interno della classe per
            //l'update)

            String query= "insert into utente (cf, nome, cognome, cittadinanza, comune_di_nascita, sesso, provincia_di_nascita, num_telefono, data_di_nascita, " +
                    "email, password, num_identificativo_ci, num_patente, num_passaporto, nome_via_domicilio, num_civico_domicilio, nome_via_residenza, " +
                    "num_civico_residenza, id_comune_residenza, id_comune_domicilio, id_via_residenza, id_via_domicilio, occupazione, reditto_annuo) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);

            statement.setString(1, ut.getCodiceFiscale());
            statement.setString(2, ut.getNome());
            statement.setString(3, ut.getCognome());
            statement.setString(4, ut.getCittadinanza());
            statement.setString(5, ut.getComuneNascita());
            statement.setString(6, ut.getSesso());
            statement.setString(7, ut.getProvNascita());
            statement.setString(8, ut.getNumTelefono());
            statement.setDate(9, ut.getDataNascita());
            statement.setString(10, ut.getEmail());
            //inserimento password
            doc= ut.getDoc();
            switch (doc.getClass().getSimpleName()) {
                case "CartaIdentita":
                    statement.setString(12, doc.getNumIdentificativo());
                    statement.setNull(13, java.sql.Types.NVARCHAR );
                    statement.setNull(14, java.sql.Types.NVARCHAR );
                    break;

                case "Patente":
                    statement.setNull(12, java.sql.Types.NVARCHAR );
                    statement.setString(13, doc.getNumIdentificativo());
                    statement.setNull(14, java.sql.Types.NVARCHAR );
                    break;

                case "Passaporto":
                    statement.setNull(12, java.sql.Types.NVARCHAR );
                    statement.setNull(13, java.sql.Types.NVARCHAR );
                    statement.setString(14, doc.getNumIdentificativo());
                    break;
            }
            res= ut.getResidenza();
            //chiamata a funzione privata che fa l'aggiunta dell'indirizzo
            dom= ut.getDomicilio();

        }catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(Utente ut){

    }
}
