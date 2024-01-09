package com.example.progettowebtest.Model;

import java.sql.Date;

public class TransazioneProxy implements Transazione{

    @Override
    public Date getDataTransazione() {
        return null;
    }

    @Override
    public double getImporto() {
        return 0;
    }

    @Override
    public String getCausale() {
        return null;
    }
}
