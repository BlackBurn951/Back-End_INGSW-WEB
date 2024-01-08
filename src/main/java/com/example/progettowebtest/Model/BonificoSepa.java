package com.example.progettowebtest.Model;

public class BonificoSepa extends Transazione{
    private int idSepa;
    private String nomeBeneficiario;
    private String cognomeBeneficiario;
    private double importo;
    private String causale;
    private String ibanDestinatario;

    public BonificoSepa(String dataTransazione, double costoTransazione, String esito, int idSepa, String nomeBeneficiario,
                        String cognomeBeneficiario, double importo, String causale, String ibanDestinatario) {
        super(dataTransazione, costoTransazione, esito);
        this.idSepa = idSepa;
        this.nomeBeneficiario = nomeBeneficiario;
        this.cognomeBeneficiario = cognomeBeneficiario;
        this.importo = importo;
        this.causale = causale;
        this.ibanDestinatario = ibanDestinatario;
    }

    public int getIdSepa() {return idSepa;}
    public String getNomeBeneficiario() {return nomeBeneficiario;}
    public String getCognomeBeneficiario() {return cognomeBeneficiario;}
    public double getImporto() {return importo;}
    public String getCausale() {return causale;}
    public String getIbanDestinatario() {return ibanDestinatario;}
}
