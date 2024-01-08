package com.example.progettowebtest.Model;

public class CartaCredito extends Carta{
    private DebitoCartaCredito debitoCarta;

    public CartaCredito(String numCarta, boolean pagamentoOnline, String dataCreazione, String dataScadenza, int cvv, boolean cartaFisica,
                        double canoneMensile, String pin, Stato statoCarta, ContoCorrente contoRiferimento, DebitoCartaCredito debitoCarta) {
        super(numCarta, pagamentoOnline, dataCreazione, dataScadenza, cvv, cartaFisica, canoneMensile, pin, statoCarta, contoRiferimento);
        this.debitoCarta = debitoCarta;
    }
}
