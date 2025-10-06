package com.example.banking.loan.ejb;

import com.example.banking.loan.dao.PretValuesDAO;
import com.example.banking.loan.model.PretValues;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * EJB Stateless pour la gestion des valeurs de prêt
 */
@Stateless
public class PretValuesEJB implements PretValuesEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(PretValuesEJB.class);

    private final PretValuesDAO pretValuesDAO;

    public PretValuesEJB() {
        this.pretValuesDAO = new PretValuesDAO();
        logger.info("EJB PretValuesEJB initialisé");
    }

    @Override
    public List<PretValues> getAllPretValues() {
        logger.info("EJB Loan - Récupération de toutes les valeurs de prêt");
        return pretValuesDAO.findAll();
    }

    @Override
    public PretValues createPretValues(PretValues pretValues) {
        logger.info("EJB Loan - Création de nouvelles valeurs de prêt");
        return pretValuesDAO.create(pretValues);
    }

    @Override
    public List<PretValues> getValuesByPret(Integer idPret) {
        logger.debug("EJB Loan - Récupération des valeurs du prêt: {}", idPret);
        return pretValuesDAO.findByPret(idPret);
    }
}