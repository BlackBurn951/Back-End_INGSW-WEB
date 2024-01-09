package com.example.progettowebtest.Model;

public class Bollettino extends DatiTransazione {
    private int idBollettino;
    private double importo;
    private String causale;
    private String numCcDest;
    private TipologiaBollettino tipoBol;

    public Bollettino(String dataTransazione, double costoTransazione, String esito, int idBollettino,
                      double importo, String causale, String numCcDest, TipologiaBollettino tipoBol) {
        super(dataTransazione, costoTransazione, esito);
        this.idBollettino = idBollettino;
        this.importo = importo;
        this.causale = causale;
        this.numCcDest = numCcDest;
        this.tipoBol = tipoBol;
    }

    public int getIdBollettino() {return idBollettino;}
    public double getImporto() {return importo;}
    public String getCausale() {return causale;}
    public String getNumCcDest() {return numCcDest;}
    public TipologiaBollettino getTipoBol() {return tipoBol;}
}
