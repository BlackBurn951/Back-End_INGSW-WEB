package com.example.progettowebtest.Model.Utente_Documenti;

import com.example.progettowebtest.Model.Indirizzo.Indirizzo;

import java.util.Vector;

public class Utente extends DatiUtente {
    private String codiceFiscale;
    private String email;
    private String password;
    private String numTelefono;
    private String occupazione;
    private double redditoAnnuo;
    private DocumentiIdentita doc;
    private Vector<Indirizzo> indirizziUtente;

    public Utente(String nome, String cognome, String cittadinanza, String comuneNascita, String sesso, String provNascita, String numTelefono, String dataNascita,
                  String codiceFiscale, String email, String password, String occupazione, double redditoAnnuo, DocumentiIdentita doc) {
        super(nome, cognome, cittadinanza, comuneNascita, sesso, provNascita, dataNascita);
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.password= password;
        this.numTelefono=numTelefono;
        this.occupazione = occupazione;
        this.redditoAnnuo = redditoAnnuo;
        this.doc = doc;
        this.indirizziUtente = new Vector<>();
    }

    //Getters dei parametri unici dell'utente
    public String getCodiceFiscale() {return codiceFiscale;}
    public String getEmail() {return email;}
    public String getPassword() {return password;} //BCrypt.hashpw(ut.getPassword(), BCrypt.gensalt(12))
    public String getNumTelefono() {return numTelefono;}
    public String getOccupazione() {return occupazione;}
    public double getRedditoAnnuo() {return redditoAnnuo;}
    public DocumentiIdentita getDoc() {return doc;}

    public void setPassword(String password) {this.password = password;}

    //Gestione indirizzi
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
