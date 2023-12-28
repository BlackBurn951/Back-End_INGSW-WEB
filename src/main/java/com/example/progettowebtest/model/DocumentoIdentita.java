package com.example.progettowebtest.model;

import java.time.LocalDate;
import java.util.Objects;

public class DocumentoIdentita {

    private String nome;
    private String cognome;
    private String luogoDiNascita;
    private LocalDate dataDiNascita;

    public DocumentoIdentita(String nome, String cognome, String luogoDiNascita, LocalDate dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.luogoDiNascita = luogoDiNascita;
        this.dataDiNascita = dataDiNascita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }

    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentoIdentita that)) return false;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getCognome(), that.getCognome()) && Objects.equals(getLuogoDiNascita(), that.getLuogoDiNascita()) && Objects.equals(getDataDiNascita(), that.getDataDiNascita());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getCognome(), getLuogoDiNascita(), getDataDiNascita());
    }
}
