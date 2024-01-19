package com.example.progettowebtest.EmailSender;

public class EmailData {
    private String nomeCognome;
    private String sender;
    private String to;
    private String subject;
    private String userId;

    private boolean eConfermaCarta;

    public boolean iseConfermaCarta() {return eConfermaCarta;}

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
