package com.example.progettowebtest.Model;

public class CartaDebito extends Carta{
    public CartaDebito(String numCarta, boolean pagamentoOnline, String dataCreazione, String dataScadenza, String cvv, boolean cartaFisica,
                       double canoneMensile, String pin, Stato statoCarta, ContoCorrente contoRiferimento) {
        super(numCarta, pagamentoOnline, dataCreazione, dataScadenza, cvv, cartaFisica, canoneMensile, pin, statoCarta, contoRiferimento);
    }
}
