package com.example.progettowebtest.Model;

public class Passaporto extends DatiUtente {

    protected Passaporto(String nome, String cognome, String cittadinanza, String comuneNascita, char sesso,
                         String provNascita, String dataNascita) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita);
    }
}
