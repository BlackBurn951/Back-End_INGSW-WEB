package com.example.progettowebtest.Model.ContoCorrente;

import com.example.progettowebtest.Model.Indirizzo.Indirizzo;
import com.example.progettowebtest.Model.Stato;
import com.example.progettowebtest.Model.Proxy.Transazione;
import com.example.progettowebtest.Model.Utente_Documenti.Utente;

import java.sql.Date;
import java.util.Vector;

public class ContoCorrente {
    private String numCC;
    private String iban;
    private String pin;
    private Date dataApertura;
    private double saldo;
    private int tassoInteresse;
    private int tariffaAnnuale;
    private Stato statoConto;
    private Indirizzo indFatturazione;
    private Utente intestatario;
    private Vector<Transazione> movimenti;

    public ContoCorrente(String numCC, String iban, String pin, String dataApertura, double saldo,
                         int tassoInteresse, int tariffaAnnuale, Indirizzo indFatturazione, Utente intestatario) {
        this.numCC = numCC;
        this.iban = iban;
        this.pin = pin;
        this.dataApertura = Date.valueOf(dataApertura);
        this.saldo = saldo;
        this.tassoInteresse = tassoInteresse;
        this.tariffaAnnuale = tariffaAnnuale;
        this.indFatturazione = indFatturazione;
        this.intestatario = intestatario;
        this.movimenti= new Vector<>();
    }
    public ContoCorrente() {
        this.movimenti= new Vector<>();
    }

    public String getNumCC() {return numCC;}
    public String getIban() {return iban;}
    public String getPin() {return pin;}
    public Date getDataApertura() {return dataApertura;}
    public double getSaldo() {return saldo;}
    public int getTassoInteresse() {return tassoInteresse;}
    public int getTariffaAnnuale() {return tariffaAnnuale;}
    public Stato getStatoConto() {return statoConto;}
    public Indirizzo getIndFatturazione() {return indFatturazione;}
    public Utente getIntestatario() {return intestatario;}
    public Vector<Transazione> getMovimenti() {return movimenti;}

    public void setNumCC(String numCC) {
        this.numCC = numCC;
    }

    public void setIban(String iban) {this.iban = iban;}
    public void setPin(String pin) {this.pin = pin;}
    public void setSaldo(double saldo) {this.saldo = saldo;}
    public void setTassoInteresse(int tassoInteresse) {this.tassoInteresse = tassoInteresse;}
    public void setTariffaAnnuale(int tariffaAnnuale) {this.tariffaAnnuale = tariffaAnnuale;}
    public void setStatoConto(Stato statoConto) {this.statoConto = statoConto;}
    public void setIndFatturazione(Indirizzo indFatturazione) {this.indFatturazione = indFatturazione;}
    public void setIntestatario(Utente intestatario) {this.intestatario = intestatario;}
    public void setDataApertura(String dataApertura) {this.dataApertura = Date.valueOf(dataApertura);}

    public void addTransazione(Transazione trans) {movimenti.add(trans);}
    public void removeTransazione(Transazione trans) {movimenti.remove(trans);}

}
