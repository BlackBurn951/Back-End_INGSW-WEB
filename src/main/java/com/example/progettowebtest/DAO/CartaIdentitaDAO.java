package com.example.progettowebtest.DAO;

import com.example.progettowebtest.Model.CartaIdentita;
import java.util.Vector;

public interface CartaIdentitaDAO {
    Vector<CartaIdentita> doRetriveAll();
    CartaIdentita doRetriveByKey(String numIdentificativo);
    boolean saveOrUpdate(CartaIdentita cd);
    boolean delete(CartaIdentita cd);
}
