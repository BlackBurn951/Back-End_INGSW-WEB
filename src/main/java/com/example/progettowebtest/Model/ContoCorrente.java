package com.example.progettowebtest.Model;

import java.sql.Date;

public class ContoCorrente {
    private String numCC;
    private String iban;
    private String pin;
    private Date dataApertura;
    private double limiteConto;
    private double saldo;
    private int tassoInteresse;
    private int tariffaAnnuale;
    private Indirizzo indFatturazione;
    private Utente intestatario;

    public ContoCorrente(String numCC, String iban, String pin, String dataApertura, double limiteConto, double saldo,
                         int tassoInteresse, int tariffaAnnuale, Indirizzo indFatturazione, Utente intestatario) {
        this.numCC = numCC;
        this.iban = iban;
        this.pin = pin;
        this.dataApertura = Date.valueOf(dataApertura);
        this.limiteConto = limiteConto;
        this.saldo = saldo;
        this.tassoInteresse = tassoInteresse;
        this.tariffaAnnuale = tariffaAnnuale;
        this.indFatturazione = indFatturazione;
        this.intestatario = intestatario;
    }

    public String getNumCC() {return numCC;}
    public String getIban() {return iban;}
    public String getPin() {return pin;}
    public Date getDataApertura() {return dataApertura;}
    public double getLimiteConto() {return limiteConto;}
    public double getSaldo() {return saldo;}
    public int getTassoInteresse() {return tassoInteresse;}
    public int getTariffaAnnuale() {return tariffaAnnuale;}
    public Indirizzo getIndFatturazione() {return indFatturazione;}
    public Utente getIntestatario() {return intestatario;}
}
