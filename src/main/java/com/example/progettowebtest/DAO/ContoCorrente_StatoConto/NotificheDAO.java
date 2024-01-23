package com.example.progettowebtest.DAO.ContoCorrente_StatoConto;

import com.example.progettowebtest.Model.ContoCorrente.Notifiche;


import java.util.Vector;

public interface NotificheDAO {
    Vector<Notifiche> doRetriveAll();
    Vector<Notifiche> doRetriveAllForCC(String numCC);
    int retriveLastId();
    Notifiche doRetriveByKey(int id);
    boolean saveOrUpdate(Notifiche not, String numCC);
    boolean delete(int id);
}
