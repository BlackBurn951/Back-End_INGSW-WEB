package com.example.progettowebtest.DAO;

import com.example.progettowebtest.DAO.Carte.CarteDAO;
import com.example.progettowebtest.DAO.Carte.CarteDAOImpl;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAO;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.ContoCorrenteDAOImpl;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.RelStatoContoDAO;
import com.example.progettowebtest.DAO.ContoCorrente_StatoConto.RelStatoContoDAOImpl;
import com.example.progettowebtest.DAO.Indirizzo.*;
import com.example.progettowebtest.DAO.Transazioni.*;
import com.example.progettowebtest.DAO.Utente_Documenti.*;

public class MagnusDAO {
    private static MagnusDAO instance= new MagnusDAO();
    private UtenteDAO utenteDAO;
    private CartaIdentitaDAO cartaIdentitaDAO;
    private PassaportoDAO passaportoDAO;
    private PatenteDAO patenteDAO;
    private DatiComuneDAO datiComuneDAO;
    private IndirizzoDAO indirizzoDAO;
    private TipoViaDAO tipoViaDAO;
    private ContoCorrenteDAO contoCorrenteDAO;
    private RelStatoContoDAO relStatoContoDAO;
    private StatoDAO statoDAO;
    private BollettinoDAO bollettinoDAO;
    private BonificoInterDAO bonificoInterDAO;
    private BonificoSepaDAO bonificoSepaDAO;
    private DepositoDAO depositoDAO;
    private PrelievoDAO prelievoDAO;
    private TipologiaBollettinoDAO tipologiaBollettinoDAO;
    private MezzoDAO mezzoDAO;
    private CarteDAO carteDAO;

    public MagnusDAO() {
        utenteDAO= new UtenteDAOImpl();
        cartaIdentitaDAO= new CartaIdentitaDAOImpl();
        passaportoDAO= new PassaportoDAOImpl();
        patenteDAO= new PatenteDAOImpl();
        datiComuneDAO= new DatiComuneDAOImpl();
        indirizzoDAO= new IndirizzoDAOImpl();
        tipoViaDAO= new TipoViaDAOImpl();
        contoCorrenteDAO= new ContoCorrenteDAOImpl();
        relStatoContoDAO= new RelStatoContoDAOImpl();
        statoDAO= new StatoDAOImpl();
        bollettinoDAO= new BollettinoDAOImpl();
        bonificoInterDAO= new BonificoInterDAOImpl();
        bonificoSepaDAO= new BonificoSepaDAOImpl();
        depositoDAO= new DepositoDAOImpl();
        prelievoDAO= new PrelievoDAOImpl();
        tipologiaBollettinoDAO= new TipologiaBollettinoDAOImpl();
        mezzoDAO= new MezzoDAOImpl();
        carteDAO= new CarteDAOImpl();
    }

    public static MagnusDAO getInstance() {
        return instance;
    }

    public UtenteDAO getUtenteDAO() {return utenteDAO;}
    public CartaIdentitaDAO getCartaIdentitaDAO() {return cartaIdentitaDAO;}
    public PassaportoDAO getPassaportoDAO() {return passaportoDAO;}
    public PatenteDAO getPatenteDAO() {return patenteDAO;}
    public DatiComuneDAO getDatiComuneDAO() {return datiComuneDAO;}
    public IndirizzoDAO getIndirizzoDAO() {return indirizzoDAO;}
    public TipoViaDAO getTipoViaDAO() {return tipoViaDAO;}
    public ContoCorrenteDAO getContoCorrenteDAO() {return contoCorrenteDAO;}
    public RelStatoContoDAO getRelStatoContoDAO() {return relStatoContoDAO;}
    public StatoDAO getStatoDAO() {return statoDAO;}
    public BollettinoDAO getBollettinoDAO() {return bollettinoDAO;}
    public BonificoInterDAO getBonificoInterDAO() {return bonificoInterDAO;}
    public BonificoSepaDAO getBonificoSepaDAO() {return bonificoSepaDAO;}
    public DepositoDAO getDepositoDAO() {return depositoDAO;}
    public PrelievoDAO getPrelievoDAO() {return prelievoDAO;}
    public TipologiaBollettinoDAO getTipologiaBollettinoDAO() {return tipologiaBollettinoDAO;}
    public MezzoDAO getMezzoDAO() {return mezzoDAO;}
    public CarteDAO getCarteDAO() {return carteDAO;}
}
