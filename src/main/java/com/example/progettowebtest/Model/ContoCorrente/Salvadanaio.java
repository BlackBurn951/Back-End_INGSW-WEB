package com.example.progettowebtest.Model.ContoCorrente;

import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;

public class Salvadanaio {
    private int idSalvadanaio;
    private double saldoAttuale;
    private double obiettivo;
    private String nomeObiettivo;
    private ContoCorrente contoRiferimento;

    public Salvadanaio(int idSalvadanaio, double saldoAttuale, double obiettivo, String nomeObiettivo, ContoCorrente contoRiferimento) {
        this.idSalvadanaio = idSalvadanaio;
        this.saldoAttuale = saldoAttuale;
        this.obiettivo = obiettivo;
        this.nomeObiettivo = nomeObiettivo;
        this.contoRiferimento = contoRiferimento;
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
    public ContoCorrente getContoRiferimento() {
        return contoRiferimento;
    }
}
