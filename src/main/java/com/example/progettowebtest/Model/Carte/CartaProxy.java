package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

import java.sql.Date;

public class CartaProxy implements Carte{
    private String numCarta;
    private boolean pagamentoOnline;
    private Date dataCreazione;
    private Date dataScadenza;
    private String cvv;
    private boolean cartaFisica;
    private double canoneMensile;
    private String pin;
    private double fido;
    private Stato statoCarta;
    private Carte cartaReale;
    private TipiCarte tipo;

    public CartaProxy(String numCarta, boolean pagamentoOnline, String dataCreazione, String dataScadenza,
                      String cvv, boolean cartaFisica, double canoneMensile, String pin, double fido, Stato statoCarta, TipiCarte tipo) {
        this.numCarta = numCarta;
        this.pagamentoOnline = pagamentoOnline;
        this.dataCreazione = Date.valueOf(dataCreazione);
        this.dataScadenza = Date.valueOf(dataScadenza);
        this.cvv = cvv;
        this.cartaFisica = cartaFisica;
        this.canoneMensile = canoneMensile;
        this.pin = pin;
        this.fido = fido;
        this.statoCarta = statoCarta;
        this.tipo = tipo;
    }

    @Override
    public String getNumCarta() {return numCarta;}

    @Override
    public boolean isPagamentoOnline() {return pagamentoOnline;}

    @Override
    public Date getDataCreazione() {return dataCreazione;}

    @Override
    public Date getDataScadenza() {return dataScadenza;}

    @Override
    public String getCvv() {return cvv;}

    @Override
    public boolean isCartaFisica() {return cartaFisica;}

    @Override
    public double getCanoneMensile() {return canoneMensile;}

    @Override
    public String getPin() {return pin;}

    @Override
    public Double getFido() {return fido;}

    @Override
    public ContoCorrente getContoRiferimento() {
        if(tipo==TipiCarte.DEBITO) {
            cartaReale = MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(numCarta, TipiCarte.DEBITO, true);
            return cartaReale.getContoRiferimento();
        }
        else{
            cartaReale = MagnusDAO.getInstance().getCarteDAO().doRetriveByKey(numCarta, TipiCarte.CREDITO, true);
            return cartaReale.getContoRiferimento();
        }
    }

    @Override
    public Stato getStatoCarta() {return statoCarta;}

    @Override
    public void setStatoCarta(Stato stato) {
        statoCarta= stato;
    }

    @Override
    public TipiCarte getTipoCarta() {
        return tipo;
    }
}
