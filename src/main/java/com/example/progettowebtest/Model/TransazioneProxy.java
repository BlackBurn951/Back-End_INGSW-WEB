package com.example.progettowebtest.Model;

import java.sql.Date;

public class TransazioneProxy implements Transazione {
    private String id;
    private Date dataTransazione;
    private double importo;
    private String causale;

    private Transazione transazioneReale= null;
    private TipoTransazione tipo;


    public TransazioneProxy(String id, Date dataTransazione, double importo, String causale, TipoTransazione tipo) {
        this.id = id;
        this.dataTransazione = dataTransazione;
        this.importo = importo;
        this.causale = causale;
        this.tipo= tipo;
    }

    @Override
    public Date getDataTransazione() {return dataTransazione;}
    @Override
    public double getImporto() {return importo;}
    @Override
    public String getCausale() {return causale;}


    @Override
    public double getCostoTransazione() {
        if(transazioneReale==null)
            instanzaTransazione();
        return transazioneReale.getCostoTransazione();
    }

    @Override
    public String getEsito() {
        if (transazioneReale==null)
            instanzaTransazione();
        return transazioneReale.getEsito();
    }

    @Override
    public String getNomeBeneficiario() {
        if(transazioneReale==null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getCognomeBeneficiario();
        else
            return;
    }

    @Override
    public String getCognomeBeneficiario() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getIbanDestinatario() {
        return null;
    }

    @Override
    public String getNumCcDest() {
        return null;
    }

    @Override
    public TipologiaBollettino getTipoBol() {
        return null;
    }

    @Override
    public String getValutaPagamento() {
        return null;
    }

    @Override
    public String getPaeseDestinatario() {
        return null;
    }

    @Override
    public Mezzo getMezzo() {
        return null;
    }

    @Override
    public Carta getCartaEsecuzione() {
        return null;
    }



    //Metodi di servizio
    private void instanzaTransazione() {
        if (tipo == TipoTransazione.BOLLETTINO) {
            //transazioneReale=
        } else if (transazioneReale instanceof BonificoInter) {
            //
        } else if (transazioneReale instanceof BonificoSepa) {
            //
        } else if (transazioneReale instanceof Deposito) {
            //
        } else if (transazioneReale instanceof Prelievo) {
            //
        }
    }

    private boolean isType(TipoTransazione types) {
        return tipo==types;
    }
}
