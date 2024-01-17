package com.example.progettowebtest.Servlet;

import com.example.progettowebtest.DAO.Indirizzo.DatiComuneDAOImpl;
import com.example.progettowebtest.DAO.MagnusDAO;
import com.example.progettowebtest.Model.Indirizzo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OttieniCittaController {

    @GetMapping("/getCitta")
    public List<String> getSuggerimentoCitta(@RequestParam("sugg") String sugg) {
        return MagnusDAO.getInstance().getDatiComuneDAO().restituisciCitta(sugg);
    }

    @GetMapping("/datiCitta")
    public List<String> getDatiCitta(@RequestParam("datiC") String datiC) {
        Vector<DatiComune> datiCitta= MagnusDAO.getInstance().getDatiComuneDAO().doRetriveByAttribute(datiC, ColonneDatiComune.NOME_COMUNE);
        Vector<String> result= new Vector<>();

        result.add(datiCitta.get(0).getCap());
        result.add(datiCitta.get(0).getProvincia());
        result.add(datiCitta.get(0).getRegione());

        return result;
    }
}
