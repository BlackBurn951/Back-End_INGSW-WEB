package com.example.progettowebtest.ClassiRequest;

public class DatiBollettino {

    private String tipologiaBollettino;
    private String numCCDest;
    private double importo;
    private String causale;

    public String getTipologiaBollettino() {
        return tipologiaBollettino;
    }

    public String getNumCCDest() {
        return numCCDest;
    }

    public double getImporto() {
        return importo;
    }

    public String getCausale() {
        return causale;
    }


    public void setTipologiaBollettino(String tipologiaBollettino) {
        this.tipologiaBollettino = tipologiaBollettino;
    }

    public void setNumCCDest(String numCCDest) {
        this.numCCDest = numCCDest;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }
}
