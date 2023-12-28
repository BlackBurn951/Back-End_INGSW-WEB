package com.example.progettowebtest.model;

import java.time.LocalDate;
import java.util.Objects;

public class CartaIdentita extends DocumentoIdentita {

    private String numeroIdentificativo;
    private LocalDate dataEmissione;
    private LocalDate dataScadenza;
    private String comuneDiRilascio;



    public CartaIdentita(String numero_identificativo, LocalDate data_emissione, LocalDate data_scadenza, String comune_di_rilascio, String nome, String cognome, String luogoDiNascita, LocalDate dataDiNascita) {
        super(nome, cognome, luogoDiNascita, dataDiNascita);
        this.numeroIdentificativo = numero_identificativo;
        this.dataEmissione = data_emissione;
        this.dataScadenza = data_scadenza;
        this.comuneDiRilascio = comune_di_rilascio;

    }


    public String getNumeroIdentificativo() {
        return numeroIdentificativo;
    }

    public void setNumeroIdentificativo(String numeroIdentificativo) {
        this.numeroIdentificativo = numeroIdentificativo;
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

    public String getComuneDiRilascio() {
        return comuneDiRilascio;
    }

    public void setComuneDiRilascio(String comuneDiRilascio) {
        this.comuneDiRilascio = comuneDiRilascio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartaIdentita that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getNumeroIdentificativo(), that.getNumeroIdentificativo()) && Objects.equals(getDataEmissione(), that.getDataEmissione()) && Objects.equals(getDataScadenza(), that.getDataScadenza()) && Objects.equals(getComuneDiRilascio(), that.getComuneDiRilascio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumeroIdentificativo(), getDataEmissione(), getDataScadenza(), getComuneDiRilascio());
    }
}
