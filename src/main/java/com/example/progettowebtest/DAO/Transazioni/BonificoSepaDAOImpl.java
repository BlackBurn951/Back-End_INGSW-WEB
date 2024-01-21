package com.example.progettowebtest.DAO.Transazioni;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.Proxy.TipoTransazione;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Proxy.TransazioneProxy;
import com.example.progettowebtest.Model.Transazioni.BonificoInter;
import com.example.progettowebtest.Model.Transazioni.BonificoSepa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class BonificoSepaDAOImpl implements BonificoSepaDAO {
    @Override
    public Vector<Transazione> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Transazione> doRetriveAllForCC(String numCC) {
        Vector<Transazione> result= new Vector<>();
        String query= "select b.id_sepa, r.data_transazione, b.importo, r.esito, b.causale from bonifico_area_sepa as b, rel_cc_bon_sepa as r where b.id_sepa= r.id_sepa";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            while(queryResult.next()) {
                result.add(new TransazioneProxy(queryResult.getInt("id_sepa"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"), queryResult.getBoolean("esito"), TipoTransazione.BONIFICOSEPA));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Transazione doRetriveByKey(int id, boolean proxy) {
        Transazione bonSep = null;
        String query;
        if(proxy)
            query= "select * from bonifico_area_sepa as b, rel_cc_bon_sepa as r where b.id_sepa= r.id_sepa";
        else
            query= "select b.id_sepa, r.data_transazione, b.importo, r.esito, b.causale from bonifico_area_sepa as b, rel_cc_bon_sepa as r where b.id_sepa=r.id_sepa";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult = statement.executeQuery();

            if (proxy && queryResult.next()){
                bonSep = new BonificoSepa(queryResult.getDate("data_transazione").toString(), queryResult.getDouble("costo_commissione"),
                        queryResult.getBoolean("esito"), queryResult.getString("nome_beneficiario"), queryResult.getString("cognome_beneficiario"),
                        queryResult.getDouble("importo"), queryResult.getString("causale"), queryResult.getString("iban_destinatario"));
            bonSep.setId(id);
            }
            else
                return new TransazioneProxy(queryResult.getInt("id_sepa"), queryResult.getDate("data_transazione").toString(), queryResult.getDouble("importo"),
                        queryResult.getString("causale"), queryResult.getBoolean("esito"), TipoTransazione.BONIFICOSEPA);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return bonSep;
    }

    @Override
    public int retriveLastId(){
        String query= "select max(id_sepa) as id from bonifico_area_sepa";
        int max;
        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            ResultSet queryResult= statement.executeQuery();

            if(queryResult.next()) {
                max = queryResult.getInt("id");
                return max;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    @Override
    public boolean saveOrUpdate(BonificoSepa bonSepa, String numCC) {
        String query="insert into bonifico_area_sepa(nome_beneficiario, cognome_beneficiario, importo, causale, iban_destinatario) values(?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setString(1, bonSepa.getNomeBeneficiario());
            statement.setString(2, bonSepa.getCognomeBeneficiario());
            statement.setDouble(3, bonSepa.getImporto());
            statement.setString(4, bonSepa.getCausale());
            statement.setString(5, bonSepa.getIbanDestinatario());

            int i = statement.executeUpdate();
            if(i >0){
                int id= retriveLastId();
                if(id!=0)
                    bonSepa.setId(id);
                else
                    return false;
                if(inserisciRelazion(bonSepa, numCC))
                    return true;
                else
                    return false;

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Transazione bon) {
        String query = "DELETE FROM rel_cc_bon_sepa WHERE id_sepa = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, bon.getId());

            if (statement.executeUpdate()>0  && eliminaTransazione(bon)) {
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Metodi di servizio
    private boolean inserisciRelazion(BonificoSepa bon, String numCC) {
        String query= "insert into rel_cc_bon_sepa(data_transazione, costo_commissione, esito, id_sepa, num_cc) " +
                "values (?, ?, ?, ?, ?)";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            statement.setDate(1, bon.getDataTransazione());
            statement.setDouble(2, bon.getCostoTransazione());
            statement.setBoolean(3, bon.getEsito());
            statement.setInt(4, bon.getId());
            statement.setString(5, numCC);

            if(statement.executeUpdate()>0)
                return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean eliminaTransazione(Transazione bon) {
        String bonificoQuery = "DELETE FROM bonifico_area_sepa WHERE id_sepa = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(bonificoQuery);
            statement.setInt(1, bon.getId());

            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
