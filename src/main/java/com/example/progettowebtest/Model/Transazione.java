package com.example.progettowebtest.Model;

import java.sql.Date;

public interface Transazione {
    Date getDataTransazione();
    double getImporto();
    String getCausale();

}
