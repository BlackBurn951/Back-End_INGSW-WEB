package com.example.progettowebtest.EmailSender;

public class EmailData {
    private String nomeCognome;
    private String sender;
    private String to;
    private String subject;
    private String userId;
    private boolean allegato;
    private String dataDiNascita;
    private String indirizzo;
    private String numeroTelefono;
    private String dataFirma;

    public String getDataFirma() {
        return dataFirma;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public boolean isAllegato() {
        return allegato;
    }


    public String getNomeCognome() {
        return nomeCognome;
    }

    public String getTo() {
        return to;
    }


    public String getSubject() {
        return subject;
    }

    public String getUserId() {
        return userId;
    }

    public String getSender() {
        return sender;
    }



}
