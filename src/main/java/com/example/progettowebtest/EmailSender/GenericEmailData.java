package com.example.progettowebtest.EmailSender;

public class GenericEmailData {
    private String nomeCognome;
    private String sender;
    private String to;
    private String subject;
    private String userId;
    private boolean allegato;

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
