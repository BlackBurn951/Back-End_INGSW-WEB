package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Connection.DbConn;
import com.example.progettowebtest.Model.ContoCorrente.Notifiche;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class NotificheDAOImpl implements NotificheDAO{
    @Override
    public Vector<Notifiche> doRetriveAll() {
        return null;
    }

    @Override
    public Vector<Notifiche> doRetriveAllForCC(String numCC) {
        Vector<Notifiche> result= new Vector<>();
        Notifiche not;

        String query="select * from notifiche where num_cc= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setString(1, numCC);

            ResultSet queryResult= statement.executeQuery();
            while(queryResult.next()) {
                not=new Notifiche(queryResult.getString("notifica"), queryResult.getBoolean("letta"));
                not.setId(queryResult.getInt("id_notifiche"));
                result.add(not);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int retriveLastId() {
        String query="select max(id_notifiche) as id from notifiche";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            ResultSet queryResult= statement.executeQuery();
            if(queryResult.next())
                return queryResult.getInt("id");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Notifiche doRetriveByKey(int id) {
        Notifiche result= null;

        String query="select * from notifiche where id_notifiche= ?";

        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            ResultSet queryResult= statement.executeQuery();
            if(queryResult.next()) {
                result = new Notifiche(queryResult.getString("notifica"), queryResult.getBoolean("letta"));
                result.setId(queryResult.getInt("id_notifiche"));
            }
            }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Notifiche not, String numCC) {
        String query= "";

        if(not.getId()>-1)
            query="insert into notifiche(id_notifiche, notifica, letta, num_cc) values (?,?,?,?)" +
                    "on conflict (id_notifiche) do update set notifica=excluded.notifica, letta=excluded.letta, num_cc=excluded.num_cc";
        else
            query= "insert into notifiche(notifica, letta, num_cc) values (?,?,?)";
        try{
            PreparedStatement statement= DbConn.getConnection().prepareStatement(query);

            if(not.getId()>-1) {
                statement.setInt(1, not.getId());
                statement.setString(2, not.getTesto());
                statement.setBoolean(3, not.isLetta());
                statement.setString(4, numCC);
            }
            else {
                statement.setString(1, not.getTesto());
                statement.setBoolean(2, not.isLetta());
                statement.setString(3, numCC);
            }

            if(statement.executeUpdate()>0) {
                if(not.getId()==-1)
                    not.setId(retriveLastId());
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM notifiche WHERE id_notifiche = ?";

        try {
            PreparedStatement statement = DbConn.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            if (statement.executeUpdate()>0) {
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
