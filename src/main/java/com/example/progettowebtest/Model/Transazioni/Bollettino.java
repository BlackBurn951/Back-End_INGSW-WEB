package com.example.progettowebtest.Model.Transazioni;

import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Proxy.Transazione;

public class Bollettino extends DatiTransazione implements Transazione {
    private int idBollettino;
    private double importo;
    private String causale;
    private String numCcDest;
    private TipologiaBollettino tipoBol;

    public Bollettino(String dataTransazione, double costoTransazione, boolean esito,
                      double importo, String causale, String numCcDest, TipologiaBollettino tipoBol) {
        super(dataTransazione, costoTransazione, esito);
        this.importo = importo;
        this.causale = causale;
        this.numCcDest = numCcDest;
        this.tipoBol = tipoBol;
    }

    public double getImporto() {return importo;}
    public String getCausale() {return causale;}
    public String getNumCcDest() {return numCcDest;}
    public TipologiaBollettino getTipoBol() {return tipoBol;}



    //Metodi implementati per il proxy
    @Override
    public int getId() {return idBollettino;}

    @Override
    public String getNomeBeneficiario() {return "ERRORE";}

    @Override
    public String getCognomeBeneficiario() {return "ERRORE";}

    @Override
    public String getIbanDestinatario() {return "ERRORE";}

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
    public Carte getCartaEsecuzione() {
        Carte result= null;
        return result;
    }
    @Override
    public void setId(int id) {
        this.idBollettino = id;
    }

    @Override
    public String getTipoTrans() {
        return "Bollettino";
    }
}
