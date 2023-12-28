package com.example.progettowebtest.model;

import java.time.LocalDate;
import java.util.Objects;

public class Patente extends DocumentoIdentita {

    private String numeroPatente;
    private LocalDate dataEmissione;
    private LocalDate dataScadenza;
    private String autoritaEmittente;


    public Patente(String numero_patente, LocalDate data_emissione, LocalDate data_scadenza, String autorita_emittente, String nome, String cognome, String luogoDiNascita, LocalDate dataDiNascita) {
        super(nome, cognome, luogoDiNascita, dataDiNascita);
        this.numeroPatente = numero_patente;
        this.dataEmissione = data_emissione;
        this.dataScadenza = data_scadenza;
        this.autoritaEmittente = autorita_emittente;

    }

    public String getNumeroPatente() {
        return numeroPatente;
    }

    public void setNumeroPatente(String numeroPatente) {
        this.numeroPatente = numeroPatente;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getAutoritaEmittente() {
        return autoritaEmittente;
    }

    public void setAutoritaEmittente(String autoritaEmittente) {
        this.autoritaEmittente = autoritaEmittente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patente patente)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getNumeroPatente(), patente.getNumeroPatente()) && Objects.equals(getDataEmissione(), patente.getDataEmissione()) && Objects.equals(getDataScadenza(), patente.getDataScadenza()) && Objects.equals(getAutoritaEmittente(), patente.getAutoritaEmittente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumeroPatente(), getDataEmissione(), getDataScadenza(), getAutoritaEmittente());
    }
}
