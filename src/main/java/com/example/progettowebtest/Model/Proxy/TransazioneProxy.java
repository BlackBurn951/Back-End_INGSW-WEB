package com.example.progettowebtest.Model.Proxy;

import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carta;
import com.example.progettowebtest.Model.Transazioni.*;

import java.sql.Date;

public class TransazioneProxy implements Transazione {
    private int id;
    private Date dataTransazione;
    private double importo;
    private String causale;

    private Transazione transazioneReale= null;
    private TipoTransazione tipo;


    public TransazioneProxy(int id, Date dataTransazione, double importo, String causale, TipoTransazione tipo) {
        this.id = id;
        this.dataTransazione = dataTransazione;
        this.importo = importo;
        this.causale = causale;
        this.tipo= tipo;
    }


    //Metodi gestibili dal PROXY
    @Override
    public int getId() {return id;}
    @Override
    public Date getDataTransazione() {return dataTransazione;}
    @Override
    public double getImporto() {return importo;}
    @Override
    public String getCausale() {return causale;}



    //Metodi gestibili da TUTTE le transazioni
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



    //Metodi gestibili solo dai BONIFICI
    @Override
    public String getNomeBeneficiario() {
        if(transazioneReale==null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getCognomeBeneficiario();
        return "ERRORE";
    }

    @Override
    public String getCognomeBeneficiario() {
        if(transazioneReale==null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getCognomeBeneficiario();
        return "ERRORE";
    }

    @Override
    public String getIbanDestinatario() {
        if(transazioneReale==null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getCognomeBeneficiario();
        return "ERRORE";
    }

    @Override
    public String getValutaPagamento() {
        if(transazioneReale==null && (isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getNumCcDest();
        return "ERRORE";
    }

    @Override
    public String getPaeseDestinatario() {
        if(transazioneReale==null && (isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getNumCcDest();
        return "ERRORE";
    }



    //Metodi gestibili solo dai BOLLETTINI
    @Override
    public String getNumCcDest() {
        if(transazioneReale==null && (isType(TipoTransazione.BOLLETTINO)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BOLLETTINO)))
            return transazioneReale.getNumCcDest();
        return "ERRORE";
    }

    @Override
    public TipologiaBollettino getTipoBol() {
        if(transazioneReale==null && (isType(TipoTransazione.BOLLETTINO)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.BOLLETTINO)))
            return transazioneReale.getTipoBol();
        return null;
    }



    //Metodi gestibili solo dai PRELIEVI e DEPOSITI
    @Override
    public Mezzo getMezzo() {
        if(transazioneReale==null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            return transazioneReale.getMezzo();
        return null;
    }

    @Override
    public Carta getCartaEsecuzione() {
        if(transazioneReale==null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            instanzaTransazione();
        else if (transazioneReale!=null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            return transazioneReale.getCartaEsecuzione();
        return null;
    }



    //Metodi di servizio
    private void instanzaTransazione() {
        if (tipo == TipoTransazione.BOLLETTINO) {
            transazioneReale= MagnusDAO.getInstance().getBollettinoDAO().doRetriveByKey(id, true);
        } else if (tipo==TipoTransazione.BONIFICOINTER) {
            transazioneReale= MagnusDAO.getInstance().getBonificoInterDAO().doRetriveByKey(id, true);
        } else if (tipo==TipoTransazione.BONIFICOSEPA) {
            transazioneReale= MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveByKey(id, true);
        } else if (tipo==TipoTransazione.DEPOSITO) {
            transazioneReale= MagnusDAO.getInstance().getDepositoDAO().doRetriveByKey(id, true);
        } else if (tipo==TipoTransazione.PRELIEVO) {
            transazioneReale= MagnusDAO.getInstance().getPrelievoDAO().doRetriveByKey(id, true);
        }
    }

    private boolean isType(TipoTransazione types) {return tipo==types;}
}
