package com.example.progettowebtest.Model.Transazioni;

import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Proxy.Transazione;

public class BonificoSepa extends DatiTransazione implements Transazione {
    private int idSepa;
    private String nomeBeneficiario;
    private String cognomeBeneficiario;
    private double importo;
    private String causale;
    private String ibanDestinatario;

    public BonificoSepa(String dataTransazione, double costoTransazione, boolean esito, int idSepa, String nomeBeneficiario,
                        String cognomeBeneficiario, double importo, String causale, String ibanDestinatario) {
        super(dataTransazione, costoTransazione, esito);
        this.idSepa = idSepa;
        this.nomeBeneficiario = nomeBeneficiario;
        this.cognomeBeneficiario = cognomeBeneficiario;
        this.importo = importo;
        this.causale = causale;
        this.ibanDestinatario = ibanDestinatario;
    }

    public String getNomeBeneficiario() {return nomeBeneficiario;}
    public String getCognomeBeneficiario() {return cognomeBeneficiario;}
    public double getImporto() {return importo;}
    public String getCausale() {return causale;}
    public String getIbanDestinatario() {return ibanDestinatario;}



    //Metodi implementati per il proxy
    @Override
    public int getId() {return idSepa;}

    @Override
    public String getNumCcDest() {return "ERRORE";}

    @Override
    public TipologiaBollettino getTipoBol() {
        TipologiaBollettino result= null;
        return result;
    }

    @Override
    public String getValutaPagamento() {return "ERRORE";}

    @Override
    public String getPaeseDestinatario() {return "ERRORE";}

    @Override
    public Mezzo getMezzo() {
        Mezzo result= null;
        return result;
    }

    @Override
    public Carta getCartaEsecuzione() {
        Carta result= null;
        return result;
    }
}
