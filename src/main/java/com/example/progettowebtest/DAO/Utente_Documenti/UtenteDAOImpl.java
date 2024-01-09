package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Connection.DbConnection;
import com.example.progettowebtest.DAO.Indirizzo.IndirizzoDAO;
import com.example.progettowebtest.DAO.Indirizzo.IndirizzoDAOImpl;
import com.example.progettowebtest.Model.*;

import java.sql.SQLException;
import java.util.Vector;
import java.sql.*;

public class UtenteDAOImpl implements UtenteDAO {
    private static UtenteDAOImpl instance;
    private CartaIdentitaDAO cartaIdentitaDAO= CartaIdentitaDAOImpl.getInstance();
    private PatenteDAO patenteDAO= PatenteDAOImpl.getInstance();
    private PassaportoDAO passaportoDAO= PassaportoDAOImpl.getInstance();
    private IndirizzoDAO indirizzoDAO= IndirizzoDAOImpl.getInstance();

    private UtenteDAOImpl() {}
    public static UtenteDAOImpl getInstance() {
        if(instance==null)
            instance= new UtenteDAOImpl();
        return instance;
    }


    @Override
    public Vector<Utente> doRetriveAll() {
        Vector<Utente> resultList = new Vector<>();

        try {
            String query = "SELECT * FROM utente";
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                Utente user = createUtente(queryResult);
                resultList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Utente doRetriveByKey(String id, IdentificativiUtente col){
        Utente result= null;
        String query;

        try{
            if(col==IdentificativiUtente.CF)
                query= "select * from utente where cf= "+id;
            else
                query= "select * from utente where email= "+id;

            PreparedStatement statement= DbConnection.getInstance().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(!queryResult.wasNull())
                result= createUtente(queryResult);

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
            String query = "INSERT INTO utente(cf, nome, cognome, cittadinanza, comune_di_nascita, sesso, provincia_di_nascita, num_telefono, data_di_nascita, " +
                    "email, password, num_identificativo_ci, num_patente, num_passaporto, nome_via_domicilio, num_civico_domicilio, nome_via_residenza, " +
                    "num_civico_residenza, id_comune_residenza, id_comune_domicilio, id_via_residenza, id_via_domicilio, occupazione, reditto_annuo) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
                    "ON CONFLICT (cf) DO UPDATE SET nome=EXCLUDED.nome, cognome=EXCLUDED.cognome, cittadinanza=EXCLUDED.cittadinanza, comune_di_nascita=EXCLUDED.comune_di_nascita, sesso=EXCLUDED.sesso, " +
                    "provincia_di_nascita=EXCLUDED.provincia_di_nascita, num_telefono=EXCLUDED.num_telefono, data_di_nascita=EXCLUDED.data_di_nascita, email=EXCLUDED.email, password=EXCLUDED.password, " +
                    "num_identificativo_ci=EXCLUDED.num_identificativo_ci, num_patente=EXCLUDED.num_patente, num_passaporto=EXCLUDED.num_passaporto, nome_via_domicilio=EXCLUDED.nome_via_domicilio, " +
                    "num_civico_domicilio=EXCLUDED.num_civico_domicilio, nome_via_residenza=EXCLUDED.nome_via_residenza, num_civico_residenza=EXCLUDED.num_civico_residenza, id_comune_residenza=EXCLUDED.id_comune_residenza, " +
                    "id_comune_domicilio=EXCLUDED.id_comune_domicilio, id_via_residenza=EXCLUDED.id_via_residenza, id_via_domicilio=EXCLUDED.id_via_domicilio, occupazione=EXCLUDED.occupazione, reddito_annuo=EXCLUDED.reddito_annuo ";

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
    public boolean delete(Utente ut) {
        boolean result = false;

        try {
            String query = "DELETE FROM utente WHERE cf= "+ut.getCodiceFiscale();
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);

            int tupleCancellate = statement.executeUpdate();

            if (tupleCancellate > 0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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

    private Utente createUtente(ResultSet query) throws SQLException{
        Utente result;

        String docId, patente, passaporto;
        DocumentiIdentita doc= null;

        docId = query.getString("num_identificativo_ci");
        patente = query.getString("num_patente");
        passaporto = query.getString("num_passaporto");

        if (docId != null)
            doc = cartaIdentitaDAO.doRetriveByKey(docId);
        else if (patente != null)
            doc = patenteDAO.doRetriveByKey(patente);
        else if (passaporto != null)
            doc = passaportoDAO.doRetriveByKey(passaporto);

        result = new Utente(query.getString("nome"), query.getString("cognome"), query.getString("cittadinanza"), query.getString("comune_di_nascita"),
                query.getString("sesso"), query.getString("provincia_di_nascita"), query.getString("num_telefono"), query.getDate("data_di_nascita").toString(),
                query.getString("cf"), query.getString("email"), query.getString("password"), query.getString("occupazione"),
                query.getDouble("reddito_annuo"), doc);

        Indirizzo res = indirizzoDAO.doRetriveByKey(query.getString("nome_via_residenza"), query.getString("num_civico_residenza"), query.getInt("id_comune_residenza"),
                query.getInt("id_via_residenza")), dom;

        String nomeVia = query.getString("nome_via_domicilio"), numCivico = query.getString("num_civico_domicilio");
        int idComune= query.getInt("id_comune_domicilio"), idTipo= query.getInt("id_via_domicilio");
        if(nomeVia!= null) {
            dom= indirizzoDAO.doRetriveByKey(nomeVia, numCivico, idComune, idTipo);
            result.addAddress(res);
            result.addAddress(dom);
        }
        else
            result.addAddress(res);

        return result;
    }
}
