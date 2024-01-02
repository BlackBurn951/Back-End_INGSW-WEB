package com.example.progettowebtest.Model;

public class CartaIdentita extends DatiUtente {
    protected CartaIdentita(String nome, String cognome, String cittadinanza, String comuneNascita, char sesso,
                            String provNascita, String dataNascita) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita);
    }
}
