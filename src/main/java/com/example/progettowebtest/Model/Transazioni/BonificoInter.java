package com.example.progettowebtest.Model.Transazioni;

import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Proxy.Transazione;

public class BonificoInter extends DatiTransazione implements Transazione {
    private int idInter;
    private String nomeBeneficiario;
    private String cognomeBeneficiario;
    private double importo;
    private String causale;
    private String ibanDestinatario;
    private String valutaPagamento;
    private String paeseDestinatario;

    public BonificoInter(String dataTransazione, double costoTransazione, boolean esito, String nomeBeneficiario,
                         String cognomeBeneficiario, double importo, String causale, String ibanDestinatario, String valutaPagamento, String paeseDestinatario) {
        super(dataTransazione, costoTransazione, esito);
        this.nomeBeneficiario = nomeBeneficiario;
        this.cognomeBeneficiario = cognomeBeneficiario;
        this.importo = importo;
        this.causale = causale;
        this.ibanDestinatario = ibanDestinatario;
        this.valutaPagamento = valutaPagamento;
        this.paeseDestinatario = paeseDestinatario;
    }

    public String getNomeBeneficiario() {return nomeBeneficiario;}
    public String getCognomeBeneficiario() {return cognomeBeneficiario;}
    public double getImporto() {return importo;}
    public String getCausale() {return causale;}
    public String getIbanDestinatario() {return ibanDestinatario;}
    public String getValutaPagamento() {return valutaPagamento;}
    public String getPaeseDestinatario() {return paeseDestinatario;}



    //Metodi implementati per il proxy
    @Override
    public int getId() {return idInter;}

    @Override
    public TipologiaBollettino getTipoBol() {
        TipologiaBollettino result= null;
        return result;
    }

    @Override
    public String getNumCcDest() {return "ERRORE";}

    @Override
    public Mezzo getMezzo() {
        Mezzo result= null;
        return result;
    }

    @Override
    public Carte getCartaEsecuzione() {
        Carte result= null;
        return result;
    }
    @Override
    public void setId(int id) {
        this.idInter = id;
    }

    @Override
    public String getTipoTrans() {
        return "BonificoInter";
    }
}
