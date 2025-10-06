package com.example.banking.central.service;

import com.example.banking.central.ejb.CentralAggregationEJB;
import com.example.banking.central.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import java.util.List;

/**
 * Service qui utilise maintenant l'EJB d'agrégation
 */
public class CentralService {
    private static final Logger logger = LoggerFactory.getLogger(CentralService.class);

    @EJB
    private CentralAggregationEJB centralAggregationEJB;

    public CompteAvecDetails getCompteAvecDetails(Integer idCompte) {
        logger.debug("Service Central - Agrégation des données pour le compte: {}", idCompte);
        return centralAggregationEJB.getCompteAvecDetails(idCompte);
    }

    public List<CompteInfo> getAllComptes() {
        logger.debug("Service Central - Récupération de tous les comptes");
        return centralAggregationEJB.getAllComptes();
    }

    public CompteInfo getCompteInfo(Integer idCompte) {
        logger.debug("Service Central - Récupération info compte: {}", idCompte);
        return centralAggregationEJB.getCompteInfo(idCompte);
    }

    public List<PretInfo> getPretsByCompte(Integer idCompte) {
        logger.debug("Service Central - Récupération prêts compte: {}", idCompte);
        return centralAggregationEJB.getPretsByCompte(idCompte);
    }

    public List<DepotInfo> getDepotsByCompte(Integer idCompte) {
        logger.debug("Service Central - Récupération dépôts compte: {}", idCompte);
        return centralAggregationEJB.getDepotsByCompte(idCompte);
    }

    public String healthCheck() {
        logger.debug("Service Central - Vérification santé des services");
        return centralAggregationEJB.healthCheck();
    }

    public Integer calculerScoreCredit(Integer idCompte) {
        logger.debug("Service Central - Calcul score crédit pour le compte: {}", idCompte);
        return centralAggregationEJB.calculerScoreCredit(idCompte);
    }
}