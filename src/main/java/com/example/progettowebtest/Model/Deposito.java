package com.example.progettowebtest.Model;

public class Deposito extends DatiTransazione implements Transazione{
    private int idDeposito;
    private double importo;
    private String casuale;
    private Mezzo mezzo;
    private Carta cartaEsecuzione;

    public Deposito(String dataTransazione, double costoTransazione, String esito, int idDeposito,
                    double importo, String casuale, Mezzo mezzo, Carta cartaEsecuzione) {
        super(dataTransazione, costoTransazione, esito);
        this.idDeposito = idDeposito;
        this.importo = importo;
        this.casuale = casuale;
        this.mezzo = mezzo;
        this.cartaEsecuzione = cartaEsecuzione;
    }

    public Mezzo getMezzo() {return mezzo;}
    public Carta getCartaEsecuzione() {return cartaEsecuzione;}
    public double getImporto() {return importo;}



    //Metodi implementati per il proxy
    @Override
    public int getId() {return idDeposito;}

    @Override
    public String getCausale() {return casuale;}

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
