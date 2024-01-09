package com.example.progettowebtest.Model;

public class BonificoInter extends DatiTransazione {
    private int idInter;
    private String nomeBeneficiario;
    private String cognomeBeneficiario;
    private double importo;
    private String causale;
    private String ibanDestinatario;
    private String valutaPagamento;
    private String paeseDestinatario;

    public BonificoInter(String dataTransazione, double costoTransazione, String esito, int idInter, String nomeBeneficiario,
                         String cognomeBeneficiario, double importo, String causale, String ibanDestinatario, String valutaPagamento, String paeseDestinatario) {
        super(dataTransazione, costoTransazione, esito);
        this.idInter = idInter;
        this.nomeBeneficiario = nomeBeneficiario;
        this.cognomeBeneficiario = cognomeBeneficiario;
        this.importo = importo;
        this.causale = causale;
        this.ibanDestinatario = ibanDestinatario;
        this.valutaPagamento = valutaPagamento;
        this.paeseDestinatario = paeseDestinatario;
    }

    public int getIdInter() {return idInter;}
    public String getNomeBeneficiario() {return nomeBeneficiario;}
    public String getCognomeBeneficiario() {return cognomeBeneficiario;}
    public double getImporto() {return importo;}
    public String getCausale() {return causale;}
    public String getIbanDestinatario() {return ibanDestinatario;}
    public String getValutaPagamento() {return valutaPagamento;}
    public String getPaeseDestinatario() {return paeseDestinatario;}
}
