package com.example.progettowebtest.ClassiRequest;

public class DatiRegistrazione {
    private String[] datiPersonali= new String[10];
    private String[] datiIndirizzo= new String[7];
    private String[] datiIndDom= new String[7];
    private String[] datiIndFat= new String[7];
    private String[] datiDocumento= new String[12];
    private String[] datiConto= new String[3];
    private String[] datiPass= new String[2];

    public String[] getDatiPersonali() {return datiPersonali;}
    public String[] getDatiIndirizzo() {return datiIndirizzo;}
    public String[] getDatiIndDom() {return datiIndDom;}
    public String[] getDatiIndFat() {return datiIndFat;}
    public String[] getDatiDocumento() {return datiDocumento;}
    public String[] getDatiConto() {return datiConto;}
    public String[] getDatiPass() {return datiPass;}
}