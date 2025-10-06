package com.example.banking.loan.service;

import com.example.banking.loan.ejb.PretEJB;
import com.example.banking.loan.ejb.PretValuesEJB;
import com.example.banking.loan.model.Pret;
import com.example.banking.loan.model.PretValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service qui utilise maintenant les EJB pour la logique métier
 */
public class PretService {
    private static final Logger logger = LoggerFactory.getLogger(PretService.class);

    // Injection des EJB
    @EJB
    private PretEJB pretEJB;

    @EJB
    private PretValuesEJB pretValuesEJB;

    public List<Pret> getAllPrets() {
        logger.debug("Service Loan - Récupération de tous les prêts via EJB");
        return pretEJB.getAllPrets();
    }

    public List<Pret> getPretsByCompte(Integer idCompte) {
        logger.debug("Service Loan - Récupération des prêts du compte {} via EJB", idCompte);
        return pretEJB.getPretsByCompte(idCompte);
    }

    public Pret createPret(Pret pret) {
        logger.info("Service Loan - Création d'un prêt via EJB");

        // Validation
        if (pret.getMontantPret() == null || pret.getMontantPret().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du prêt doit être positif");
        }

        if (pret.getTypePret() == null || pret.getTypePret().trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de prêt est requis");
        }

        return pretEJB.createPret(pret);
    }

    public Pret getPretById(Integer id) {
        logger.debug("Service Loan - Recherche du prêt {} via EJB", id);
        return pretEJB.getPretById(id);
    }

    public List<PretValues> getPretValuesByPret(Integer idPret) {
        logger.debug("Service Loan - Récupération des valeurs du prêt {} via EJB", idPret);
        return pretEJB.getPretValuesByPret(idPret);
    }

    public Double calculerMensualite(BigDecimal montant, BigDecimal taux, Integer dureeMois) {
        logger.info("Service Loan - Calcul de mensualité via EJB");

        // Conversion BigDecimal to Double pour l'EJB
        return pretEJB.calculerMensualite(
                montant.doubleValue(),
                taux.doubleValue(),
                dureeMois
        );
    }
}