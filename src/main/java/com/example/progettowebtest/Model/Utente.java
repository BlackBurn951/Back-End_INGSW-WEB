package com.example.progettowebtest.Model;

import java.util.Vector;

public class Utente extends DatiUtente{
    private String codiceFiscale;
    private String email;
    private String numTelefono;
    private String occupazione;
    private double redditoAnnuo;
    private DocumentiIdentita doc;
    private Vector<Indirizzo> indirizziUtente;

    public Utente(String nome, String cognome, String cittadinanza, String comuneNascita, char sesso, String provNascita, String numTelefono, String dataNascita,
                  String codiceFiscale, String email, String occupazione, double redditoAnnuo, DocumentiIdentita doc) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita);
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.numTelefono=numTelefono;
        this.occupazione = occupazione;
        this.redditoAnnuo = redditoAnnuo;
        this.doc = doc;
    }

    public DocumentiIdentita getDoc() {return doc;}

    public void addAddress(Indirizzo ind) {
        if(indirizziUtente.size()>=2)
            return;
        indirizziUtente.add(ind);
    }

    public Indirizzo getResidenza() {
        if(indirizziUtente.isEmpty())
            return null;
        return indirizziUtente.get(0);
    }

    public Indirizzo getDomicilio() {
        if(indirizziUtente.size()<2)
            return null;
        return indirizziUtente.get(1);
    }
}
