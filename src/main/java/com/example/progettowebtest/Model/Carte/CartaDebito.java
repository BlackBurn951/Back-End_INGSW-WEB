package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

public class CartaDebito extends Carta implements Carte{
    public CartaDebito(String numCarta, boolean pagamentoOnline, String dataCreazione, String dataScadenza, String cvv, boolean cartaFisica,
                       double canoneMensile, String pin, Stato statoCarta, ContoCorrente contoRiferimento) {
        super(numCarta, pagamentoOnline, dataCreazione, dataScadenza, cvv, cartaFisica, canoneMensile, pin, statoCarta, contoRiferimento);
    }

    @Override
    public Double getFido() {return -1.0;}

    @Override
    public void setFido(double fido) {
    }

    @Override
    public TipiCarte getTipoCarta() {
        return TipiCarte.DEBITO;
    }
}
