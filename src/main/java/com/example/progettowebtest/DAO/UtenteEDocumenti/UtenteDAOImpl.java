package com.example.progettowebtest.DAO.UtenteEDocumenti;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.DAO.IndirizzoECorrelati.IndirizzoDAOImpl;
import com.example.progettowebtest.DAO.IndirizzoECorrelati.TipoViaDAOImpl;
import com.example.progettowebtest.Model.*;

import java.sql.SQLException;
import java.util.Vector;
import java.sql.*;

public class UtenteDAOImpl implements UtenteDAO {
    private static IndirizzoDAOImpl indirizzoDAO = new IndirizzoDAOImpl();
    private static TipoViaDAOImpl viaDAO = new TipoViaDAOImpl();

    //private static DocumentiIdentita documentiIdentitaDAO = new DocumentiIdentita();

    @Override
    public Vector<Utente> doRetriveAll(){
        return null;
    }

    @Override
    public Utente doRetriveByKey(String cf){
        Utente result= null;
        //documenti id
        String docId, patente, passaporto = "";
        try{
            String query= "select * from utente where cf= "+cf;
            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(!queryResult.wasNull()){
                docId = queryResult.getString("num_identificativo_ci");
                patente = queryResult.getString("num_patente");
                passaporto = queryResult.getString("num_passaporto");

                if(docId != null){
                    CartaIdentita cartaIdentita = new CartaIdentita()
                }
                if(patente != null){}
                if(passaporto != null){}
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Utente ut) {
        Indirizzo res, dom;
        boolean result= true;
        try {
            //chimata al doRetriveByKey per capire se fare un update o una insert (funzione private all'interno della classe per
            //l'update)

            String query= "insert into utente(cf, nome, cognome, cittadinanza, comune_di_nascita, sesso, provincia_di_nascita, num_telefono, data_di_nascita, " +
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
            statement.setString(11, ut.getPassword()); ;

            inserisciDocumento(statement, ut.getDoc());

            res= ut.getResidenza();
            statement.setString(17, res.getNomeVia());
            statement.setString(18, res.getNumCivico());
            statement.setInt(19, res.getComune().getIdComune());
            statement.setInt(21, res.getTipologiaVia().getIdVia());

            dom= ut.getDomicilio();
            if(dom!=null) {
                statement.setString(15, dom.getNomeVia());
                statement.setString(16, dom.getNumCivico());
                statement.setInt(20, dom.getComune().getIdComune());
                statement.setInt(22, dom.getTipologiaVia().getIdVia());
            }
            else {
                statement.setNull(15, Types.NULL);
                statement.setNull(16, Types.NULL);
                statement.setNull(20, Types.NULL);
                statement.setNull(22, Types.NULL);
            }

            statement.setString(23, ut.getOccupazione());
            statement.setDouble(24, ut.getRedditoAnnuo());
          
            statement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
            result= false;
        }
        return result;
    }

    @Override
    public boolean delete(Utente ut){
        return true;
    }



    //Metodi di servizio
    private void inserisciDocumento(PreparedStatement statement, DocumentiIdentita doc) throws SQLException{
        switch (doc.getClass().getSimpleName()) {
            case "CartaIdentita":
                statement.setString(12, doc.getNumIdentificativo());
                statement.setNull(13, Types.NULL);
                statement.setNull(14, Types.NULL);
                break;

            case "Patente":
                statement.setNull(12, Types.NULL);
                statement.setString(13, doc.getNumIdentificativo());
                statement.setNull(14, Types.NULL);
                break;

            case "Passaporto":
                statement.setNull(12, Types.NULL);
                statement.setNull(13, Types.NULL);
                statement.setString(14, doc.getNumIdentificativo());
                break;
        }
    }
}
