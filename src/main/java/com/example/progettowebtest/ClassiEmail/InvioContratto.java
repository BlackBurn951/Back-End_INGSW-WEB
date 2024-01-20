package com.example.progettowebtest.ClassiEmail;

public class InvioContratto {
    private String nomeCognome;
    private String dataNascita;
    private String indirizzo;
    private String numTelefono;
    private String dataFirma;
    private String pinConto;

    public InvioContratto(String nomeCognome, String dataNascita, String indirizzo, String numTelefono, String dataFirma, String pinConto) {
        this.nomeCognome = nomeCognome;
        this.dataNascita = dataNascita;
        this.indirizzo = indirizzo;
        this.numTelefono = numTelefono;
        this.dataFirma = dataFirma;
        this.pinConto = pinConto;
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public String getDataFirma() {
        return dataFirma;
    }

    public String getPinConto() {
        return pinConto;
    }
}
