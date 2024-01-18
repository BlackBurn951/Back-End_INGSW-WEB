package com.example.progettowebtest.Model.Transazioni;

import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Proxy.Transazione;

public class Prelievo extends DatiTransazione implements Transazione {
    private int idPrelievo;
    private double importo;
    private Mezzo mezzo;
    private Carta cartaEsecuzione;

    public Prelievo(String dataTransazione, double costoTransazione, boolean esito, int idPrelievo,
                    double importo, Mezzo mezzo, Carta cartaEsecuzione) {
        super(dataTransazione, costoTransazione, esito);
        this.idPrelievo = idPrelievo;
        this.importo = importo;
        this.mezzo = mezzo;
        this.cartaEsecuzione = cartaEsecuzione;
    }

    public double getImporto() {return importo;}
    public Mezzo getMezzo() {return mezzo;}
    public Carta getCartaEsecuzione() {return cartaEsecuzione;}


    //Metodi implementati per il proxy

    @Override
    public int getId() {return idPrelievo;}

    @Override
    public String getCausale() {return "ERRORE";}

    @Override
    public String getNomeBeneficiario() {return "ERRORE";}

    @Override
    public String getCognomeBeneficiario() {return "ERRORE";}

    @Override
    public String getIbanDestinatario() {return "ERRORE";}

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
}
