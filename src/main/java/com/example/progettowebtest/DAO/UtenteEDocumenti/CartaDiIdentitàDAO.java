package com.example.progettowebtest.DAO.UtenteEDocumenti;

import com.example.progettowebtest.Model.CartaIdentita;

import java.util.Vector;

public interface CartaDiIdentitàDAO{

    Vector<CartaIdentita> doRetriveAll();
    CartaIdentita doRetriveByKey(String num_identificativo);
    boolean saveorUpdate(CartaIdentita cd);
    boolean deleta(CartaIdentita cd);
}
