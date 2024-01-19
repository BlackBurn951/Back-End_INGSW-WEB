package com.example.progettowebtest.Model.Transazioni;

import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Proxy.Transazione;

public class Deposito extends DatiTransazione implements Transazione {
    private int idDeposito;
    private double importo;
    private Mezzo mezzo;
    private Carte cartaEsecuzione;

    public Deposito(String dataTransazione, double costoTransazione, boolean esito, int idDeposito,
                    double importo, Mezzo mezzo, Carte cartaEsecuzione) {
        super(dataTransazione, costoTransazione, esito);
        this.idDeposito = idDeposito;
        this.importo = importo;
        this.mezzo = mezzo;
        this.cartaEsecuzione = cartaEsecuzione;
    }

    public Mezzo getMezzo() {return mezzo;}
    public Carte getCartaEsecuzione() {return cartaEsecuzione;}
    public double getImporto() {return importo;}



    //Metodi implementati per il proxy
    @Override
    public int getId() {return idDeposito;}

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
    @Override
    public void setId(int id) {
        this.idDeposito = id;
    }
}
