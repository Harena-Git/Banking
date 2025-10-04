package com.example.banking.loan.ejb;

import com.example.banking.loan.dao.PretDAO;
import com.example.banking.loan.model.Pret;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Stateless
public class PretEJB implements PretEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(PretEJB.class);

    private final PretDAO pretDAO;

    public PretEJB() {
        this.pretDAO = new PretDAO();
    }

    @Override
    public List<Pret> getAllPrets() {
        return pretDAO.findAll();
    }

    @Override
    public List<Pret> getPretsByCompte(Integer idCompte) {
        logger.info("EJB Loan - Récupération des prêts pour le compte: {}", idCompte);
        return pretDAO.findByCompteCourant(idCompte);
    }

    @Override
    public Pret createPret(Pret pret) {
        return pretDAO.create(pret);
    }

    @Override
    public Pret getPretById(Integer id) {
        return pretDAO.findById(id);
    }
}