package com.example.progettowebtest.Model;

import com.example.progettowebtest.DAO.ContoCorrenteDAO;

public class Salvadanaio {

    private int idSalvadanaio;
    private double saldoAttuale;
    private double obiettivo;
    private String nomeObiettivo;
    private ContoCorrenteDAO conto;

    public Salvadanaio(int idSalvadanaio, double saldoAttuale, double obiettivo, String nomeObiettivo, ContoCorrenteDAO conto) {
        this.idSalvadanaio = idSalvadanaio;
        this.saldoAttuale = saldoAttuale;
        this.obiettivo = obiettivo;
        this.nomeObiettivo = nomeObiettivo;
        this.conto = conto;
    }

    public int getIdSalvadanaio() {
        return idSalvadanaio;
    }

    public double getSaldoAttuale() {
        return saldoAttuale;
    }

    public double getObiettivo() {
        return obiettivo;
    }

    public String getNomeObiettivo() {
        return nomeObiettivo;
    }

    public ContoCorrenteDAO getConto() {
        return conto;
    }
}
