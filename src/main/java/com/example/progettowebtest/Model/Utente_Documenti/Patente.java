package com.example.progettowebtest.Model.Utente_Documenti;

import com.example.progettowebtest.Model.Utente_Documenti.DocumentiIdentita;

public class Patente extends DocumentiIdentita {
    public Patente(String nome, String cognome, String cittadinanza, String comuneNascita, String sesso,
                   String provNascita, String dataNascita, String numIdentificativo, String dataEmissione, String dataScadenza, String entitaRilascio) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita, numIdentificativo, dataEmissione, dataScadenza, entitaRilascio);
    }
}
