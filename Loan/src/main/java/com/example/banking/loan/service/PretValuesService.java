package com.example.banking.loan.service;

import com.example.banking.loan.ejb.PretValuesEJB;
import com.example.banking.loan.model.PretValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import java.util.List;

/**
 * Service pour la gestion des valeurs de prêt utilisant EJB
 */
public class PretValuesService {
    private static final Logger logger = LoggerFactory.getLogger(PretValuesService.class);

    @EJB
    private PretValuesEJB pretValuesEJB;

    public List<PretValues> getAllPretValues() {
        logger.debug("Service Loan - Récupération de toutes les valeurs via EJB");
        return pretValuesEJB.getAllPretValues();
    }

    public PretValues createPretValues(PretValues pretValues) {
        logger.info("Service Loan - Création de valeurs de prêt via EJB");

        // Validation
        if (pretValues.getPourcentagePretValues() == null) {
            throw new IllegalArgumentException("Le pourcentage est requis");
        }

        if (pretValues.getDureePretValues() == null || pretValues.getDureePretValues() <= 0) {
            throw new IllegalArgumentException("La durée doit être positive");
        }

        return pretValuesEJB.createPretValues(pretValues);
    }

    public List<PretValues> getValuesByPret(Integer idPret) {
        logger.debug("Service Loan - Récupération des valeurs du prêt {} via EJB", idPret);
        return pretValuesEJB.getValuesByPret(idPret);
    }
}