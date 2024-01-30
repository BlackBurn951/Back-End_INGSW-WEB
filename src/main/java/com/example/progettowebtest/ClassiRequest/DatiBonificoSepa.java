package com.example.progettowebtest.ClassiRequest;

public class DatiBonificoSepa {

    private String nomeBeneficiario;
    private String cognomeBeneficiario;
    private double importoSepa;
    private String causaleSepa;
    private String ibanDestinatarioSepa;

    public String getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    public String getCognomeBeneficiario() {
        return cognomeBeneficiario;
    }

    public double getImportoSepa() {
        return importoSepa;
    }

    public String getCausaleSepa() {
        return causaleSepa;
    }

    public String getIbanDestinatarioSepa() {
        return ibanDestinatarioSepa;
    }

    public void setNomeBeneficiario(String nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
    }

    public void setCognomeBeneficiario(String cognomeBeneficiario) {
        this.cognomeBeneficiario = cognomeBeneficiario;
    }

    public void setImportoSepa(double importoSepa) {
        this.importoSepa = importoSepa;
    }

    public void setCausaleSepa(String causaleSepa) {
        this.causaleSepa = causaleSepa;
    }

    public void setIbanDestinatarioSepa(String ibanDestinatarioSepa) {
        this.ibanDestinatarioSepa = ibanDestinatarioSepa;
    }
}
