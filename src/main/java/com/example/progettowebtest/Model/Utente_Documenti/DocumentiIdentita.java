package com.example.progettowebtest.Model.Utente_Documenti;

import com.example.progettowebtest.Model.Utente_Documenti.DatiUtente;

import java.sql.Date;

public abstract class DocumentiIdentita extends DatiUtente {
    private String numIdentificativo;
    private Date dataEmissione;
    private Date dataScadenza;
    private String entitaRilascio;

    protected DocumentiIdentita(String nome, String cognome, String cittadinanza, String comuneNascita, String sesso, String provNascita,
                             String dataNascita, String numIdentificativo, String dataEmissione, String dataScadenza, String entitaRilascio) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita);
        this.numIdentificativo = numIdentificativo;
        this.dataEmissione = Date.valueOf(dataEmissione);
        this.dataScadenza = Date.valueOf(dataScadenza);
        this.entitaRilascio = entitaRilascio;
    }

    public String getNumIdentificativo() {return numIdentificativo;}

    public Date getDataEmissione() {return dataEmissione;}

    public Date getDataScadenza() {return dataScadenza;}

    public String getEntitaRilascio() {return entitaRilascio;}
}
