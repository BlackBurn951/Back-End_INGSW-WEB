package com.example.progettowebtest.EmailSender;


public class EmailDataWithAttachment extends EmailData {
    String dataDiNascita;
    String indirizzo;
    String numeroTelefono;
    String dataFirma;

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


}
