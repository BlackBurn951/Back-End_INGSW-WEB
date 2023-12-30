package com.example.progettowebtest.Model;

import java.time.LocalDate;


public class Utente extends DatiAnagrafici {

    private int id_utente;
    private String nome;
    private String cognome;
    private String cellulare;
    private String codice_fiscale;
    private int tipo_documento; //0 per carta d'identità 1 per patente


    public Utente(int id_utente, String nome, String cognome, String cellulare, String codice_fiscale, String cittadinanza, String sesso, String nazioneDiNascita, String comuneDiNascita, LocalDate dataDiNascita, String provincia_di_nascita, String nazione_residenza_fiscale, int tipo_documento) {
        super(cittadinanza, sesso, nazioneDiNascita, comuneDiNascita, dataDiNascita, provincia_di_nascita, nazione_residenza_fiscale);
        this.id_utente = id_utente;
        this.nome = nome;
        this.cognome = cognome;
        this.cellulare = cellulare;
        this.codice_fiscale = codice_fiscale;
        this.tipo_documento = tipo_documento;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }



    public int getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(int tipo_documento) {
        this.tipo_documento = tipo_documento;
    }


}
