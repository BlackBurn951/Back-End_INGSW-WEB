package com.example.progettowebtest.Model.Proxy;

import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Carte.Carte;
import com.example.progettowebtest.Model.Transazioni.*;

import java.sql.Date;

public class TransazioneProxy implements Transazione {
    private int id;
    private Date dataTransazione;
    private double importo;
    private String causale;
    private boolean esito;

    private Transazione transazioneReale = null;
    private TipoTransazione tipo;

    public TransazioneProxy(int id, String dataTransazione, double importo, String causale, boolean esito ,TipoTransazione tipo) {
        this.id = id;
        this.dataTransazione = Date.valueOf(dataTransazione);
        this.importo = importo;
        this.causale = causale;
        this.esito= esito;
        this.tipo = tipo;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Date getDataTransazione() {
        return dataTransazione;
    }

    @Override
    public double getImporto() {
        return importo;
    }

    @Override
    public String getCausale() {
        return causale;
    }

    @Override
    public boolean getEsito() {
        return esito;
    }


    @Override
    public double getCostoTransazione() {
        if (transazioneReale == null) {
            instanzaTransazione();
            if (transazioneReale == null) {
                return 0.0;
            }
        }
        return transazioneReale.getCostoTransazione();
    }



    @Override
    public String getNomeBeneficiario() {
        if (transazioneReale == null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getNomeBeneficiario();
        return "ERRORE";
    }

    @Override
    public String getCognomeBeneficiario() {
        if (transazioneReale == null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getCognomeBeneficiario();
        return "ERRORE";
    }

    @Override
    public String getIbanDestinatario() {
        if (transazioneReale == null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BONIFICOSEPA) || isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getIbanDestinatario();
        return "ERRORE";
    }

    @Override
    public String getValutaPagamento() {
        if (transazioneReale == null && (isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getValutaPagamento();
        return "ERRORE";
    }

    @Override
    public String getPaeseDestinatario() {
        if (transazioneReale == null && (isType(TipoTransazione.BONIFICOINTER)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BONIFICOINTER)))
            return transazioneReale.getPaeseDestinatario();
        return "ERRORE";
    }

    @Override
    public String getNumCcDest() {
        if (transazioneReale == null && (isType(TipoTransazione.BOLLETTINO)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BOLLETTINO)))
            return transazioneReale.getNumCcDest();
        return "ERRORE";
    }

    @Override
    public TipologiaBollettino getTipoBol() {
        if (transazioneReale == null && (isType(TipoTransazione.BOLLETTINO)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.BOLLETTINO)))
            return transazioneReale.getTipoBol();
        return null;
    }

    @Override
    public Mezzo getMezzo() {
        if (transazioneReale == null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            return transazioneReale.getMezzo();
        return null;
    }

    @Override
    public Carte getCartaEsecuzione() {
        if (transazioneReale == null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            instanzaTransazione();
        else if (transazioneReale != null && (isType(TipoTransazione.DEPOSITO) || isType(TipoTransazione.PRELIEVO)))
            return transazioneReale.getCartaEsecuzione();
        return null;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTipoTrans() {
        String result= "";
        if (tipo == TipoTransazione.BOLLETTINO) {
            result= "Bollettino";
        } else if (tipo == TipoTransazione.BONIFICOINTER) {
            result= "BonificoInter";
        } else if (tipo == TipoTransazione.BONIFICOSEPA) {
            result= "BonificoSepa";
        } else if (tipo == TipoTransazione.DEPOSITO) {
            result= "Deposito";
        } else if (tipo == TipoTransazione.PRELIEVO) {
            result= "Prelievo";
        }
        return result;
    }

    private void instanzaTransazione() {
        if (tipo == TipoTransazione.BOLLETTINO) {
            transazioneReale = MagnusDAO.getInstance().getBollettinoDAO().doRetriveByKey(id, true);
            if(transazioneReale!= null){
                System.out.println("ISTANZIATO BOLLETTINO");
            }
        } else if (tipo == TipoTransazione.BONIFICOINTER) {
            transazioneReale = MagnusDAO.getInstance().getBonificoInterDAO().doRetriveByKey(id, true);
            if(transazioneReale!= null){
                System.out.println("ISTANZIATO INt");
            }
        } else if (tipo == TipoTransazione.BONIFICOSEPA) {
            transazioneReale = MagnusDAO.getInstance().getBonificoSepaDAO().doRetriveByKey(id, true);
            if(transazioneReale!= null){
                System.out.println("ISTANZIATO SEPA");
            }
        } else if (tipo == TipoTransazione.DEPOSITO) {
            transazioneReale = MagnusDAO.getInstance().getDepositoDAO().doRetriveByKey(id, true);
            if(transazioneReale!= null){
                System.out.println("ISTANZIATO DEPOSITO");
            }
        } else if (tipo == TipoTransazione.PRELIEVO) {
            transazioneReale = MagnusDAO.getInstance().getPrelievoDAO().doRetriveByKey(id, true);
            if(transazioneReale!= null){
                System.out.println("ISTANZIATO PRELIEVO");
            }
        }
    }

    private boolean isType(TipoTransazione types) {
        return tipo == types;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;

        TransazioneProxy trans = (TransazioneProxy) o;
        // qui facciamo il controllo che le due persone siano uguali
        return id==trans.getId() && this.getTipoTrans().equals(trans.getTipoTrans());
    }
}
