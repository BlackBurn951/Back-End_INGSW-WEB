package com.example.progettowebtest.model;

import java.time.LocalDate;
import java.util.Objects;

public class DatiAnagrafici {

    private String cittadinanza;
    private String sesso;
    private String nazioneDiNascita;
    private String comuneDiNascita;
    private LocalDate dataDiNascita;
    private String provinciaDiNascita;
    private String nazioneResidenzaFiscale;

    public DatiAnagrafici(String cittadinanza, String sesso, String nazioneDiNascita, String comuneDiNascita, LocalDate dataDiNascita, String provinciaDiNascita, String nazioneResidenzaFiscale) {
        this.cittadinanza = cittadinanza;
        this.sesso = sesso;
        this.nazioneDiNascita = nazioneDiNascita;
        this.comuneDiNascita = comuneDiNascita;
        this.dataDiNascita = dataDiNascita;
        this.provinciaDiNascita = provinciaDiNascita;
        this.nazioneResidenzaFiscale = nazioneResidenzaFiscale;
    }

    public String getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(String cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getNazioneDiNascita() {
        return nazioneDiNascita;
    }

    public void setNazioneDiNascita(String nazioneDiNascita) {
        this.nazioneDiNascita = nazioneDiNascita;
    }

    public String getComuneDiNascita() {
        return comuneDiNascita;
    }

    public void setComuneDiNascita(String comuneDiNascita) {
        this.comuneDiNascita = comuneDiNascita;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getProvinciaDiNascita() {
        return provinciaDiNascita;
    }

    public void setProvinciaDiNascita(String provinciaDiNascita) {
        this.provinciaDiNascita = provinciaDiNascita;
    }

    public String getNazioneResidenzaFiscale() {
        return nazioneResidenzaFiscale;
    }

    public void setNazioneResidenzaFiscale(String nazioneResidenzaFiscale) {
        this.nazioneResidenzaFiscale = nazioneResidenzaFiscale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatiAnagrafici that)) return false;
        return Objects.equals(getCittadinanza(), that.getCittadinanza()) && Objects.equals(getSesso(), that.getSesso()) && Objects.equals(getNazioneDiNascita(), that.getNazioneDiNascita()) && Objects.equals(getComuneDiNascita(), that.getComuneDiNascita()) && Objects.equals(getDataDiNascita(), that.getDataDiNascita()) && Objects.equals(getProvinciaDiNascita(), that.getProvinciaDiNascita()) && Objects.equals(getNazioneResidenzaFiscale(), that.getNazioneResidenzaFiscale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCittadinanza(), getSesso(), getNazioneDiNascita(), getComuneDiNascita(), getDataDiNascita(), getProvinciaDiNascita(), getNazioneResidenzaFiscale());
    }
}
