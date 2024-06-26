package com.example.progettowebtest.DAO.Utente_Documenti;

import com.example.progettowebtest.Model.Utente_Documenti.CartaIdentita;
import java.util.Vector;

public interface CartaIdentitaDAO {
    Vector<CartaIdentita> doRetriveAll();
    CartaIdentita doRetriveByKey(String numIdentificativo);
    boolean saveOrUpdate(CartaIdentita cd);
    boolean delete(CartaIdentita cd);
}
