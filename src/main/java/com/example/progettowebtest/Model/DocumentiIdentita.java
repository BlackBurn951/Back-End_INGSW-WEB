package com.example.progettowebtest.Model;

public abstract class DocumentiIdentita extends DatiUtente{
    private String numIdentificativo;
    private String dataEmissione;
    private String dataScadenza;
    private String entitaRilascio;

    protected DocumentiIdentita(String nome, String cognome, String cittadinanza, String comuneNascita, char sesso, String provNascita,
                             String dataNascita, String numIdentificativo, String dataEmissione, String dataScadenza, String entitaRilascio) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita);
        this.numIdentificativo = numIdentificativo;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.entitaRilascio = entitaRilascio;
    }

    public String getNumIdentificativo() {return numIdentificativo;}

    public String getDataEmissione() {return dataEmissione;}

    public String getDataScadenza() {return dataScadenza;}

    public String getEntitaRilascio() {return entitaRilascio;}
}
