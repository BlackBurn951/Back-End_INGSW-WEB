package com.example.progettowebtest.Model.Carte;

import com.example.progettowebtest.Model.ContoCorrente.ContoCorrente;
import com.example.progettowebtest.Model.Stato;

public class CartaCredito extends Carta implements Carte{
    private double fido;

    public CartaCredito(String numCarta, boolean pagamentoOnline, String dataCreazione, String dataScadenza, String cvv, boolean cartaFisica,
                        double canoneMensile, String pin, Stato statoCarta, ContoCorrente contoRiferimento, double fido) {
        super(numCarta, pagamentoOnline, dataCreazione, dataScadenza, cvv, cartaFisica, canoneMensile, pin, statoCarta, contoRiferimento);
        this.fido = fido;
    }

    @Override
    public Double getFido() {
        return fido;
    }
}
