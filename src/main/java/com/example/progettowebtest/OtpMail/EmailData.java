package com.example.progettowebtest.OtpMail;

public class EmailData {
    private String destinatario;
    private String oggetto;
    private String testo;

    public EmailData(String destinatario, String oggetto, String testo) {
        this.destinatario = destinatario;
        this.oggetto = oggetto;
        this.testo = testo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
