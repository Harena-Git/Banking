package com.example.banking.central.ejb;

import com.example.banking.central.client.CurrentAccountEJBClient;
import com.example.banking.central.client.LoanEJBClient;
import com.example.banking.central.client.DepositRestClient;
import com.example.banking.central.model.*;
import com.example.banking.central.dao.CacheCompteDAO;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * EJB Stateless pour l'agrégation des données des microservices
 */
@Stateless
public class CentralAggregationEJB implements CentralAggregationEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(CentralAggregationEJB.class);

    private final CurrentAccountEJBClient currentAccountClient;
    private final LoanEJBClient loanClient;
    private final DepositRestClient depositClient;
    private final CacheCompteDAO cacheCompteDAO;

    public CentralAggregationEJB() {
        // Charger la configuration
        Properties config = new Properties();
        try {
            config.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            logger.warn("Fichier config.properties non trouvé, utilisation des valeurs par défaut");
            config.setProperty("currentaccount.url", "http://localhost:8081/currentaccount");
            config.setProperty("loan.url", "http://localhost:8082/loan");
            config.setProperty("deposit.url", "http://localhost:8084");
        }

        this.currentAccountClient = new CurrentAccountEJBClient(config);
        this.loanClient = new LoanEJBClient(config);
        this.depositClient = new DepositRestClient(config);
        this.cacheCompteDAO = new CacheCompteDAO();

        logger.info("EJB CentralAggregationEJB initialisé");
    }

    @Override
    public CompteAvecDetails getCompteAvecDetails(Integer idCompte) {
        logger.info("EJB Central - Agrégation des données pour le compte: {}", idCompte);

        try {
            // Vérifier d'abord le cache
            CompteAvecDetails cached = cacheCompteDAO.findByCompteId(idCompte);
            if (cached != null) {
                logger.debug("Données trouvées en cache pour le compte: {}", idCompte);
                return cached;
            }

            // 1. Récupérer les infos du compte courant
            CompteInfo compte = currentAccountClient.getCompteById(idCompte);
            if (compte == null) {
                logger.warn("Compte non trouvé: {}", idCompte);
                return null;
            }

            // 2. Récupérer les prêts du compte
            List<PretInfo> prets = loanClient.getPretsByCompte(idCompte);
            logger.debug("Trouvé {} prêts pour le compte {}", prets.size(), idCompte);

            // 3. Récupérer les dépôts du compte
            List<DepotInfo> depots = depositClient.getDepotsByCompte(idCompte);
            logger.debug("Trouvé {} dépôts pour le compte {}", depots.size(), idCompte);

            // 4. Agrégation des données
            CompteAvecDetails compteAvecDetails = new CompteAvecDetails(compte, prets, depots);

            // 5. Mettre en cache
            cacheCompteDAO.saveOrUpdate(compteAvecDetails);

            logger.info("Agrégation terminée pour le compte: {}", idCompte);
            return compteAvecDetails;

        } catch (Exception e) {
            logger.error("Erreur lors de l'agrégation des données pour le compte {}", idCompte, e);
            throw new RuntimeException("Erreur d'agrégation des données", e);
        }
    }

    @Override
    public List<CompteInfo> getAllComptes() {
        logger.info("EJB Central - Récupération de tous les comptes");
        return currentAccountClient.getAllComptes();
    }

    @Override
    public CompteInfo getCompteInfo(Integer idCompte) {
        logger.debug("EJB Central - Récupération info compte: {}", idCompte);
        return currentAccountClient.getCompteById(idCompte);
    }

    @Override
    public List<PretInfo> getPretsByCompte(Integer idCompte) {
        logger.debug("EJB Central - Récupération prêts compte: {}", idCompte);
        return loanClient.getPretsByCompte(idCompte);
    }

    @Override
    public List<DepotInfo> getDepotsByCompte(Integer idCompte) {
        logger.debug("EJB Central - Récupération dépôts compte: {}", idCompte);
        return depositClient.getDepotsByCompte(idCompte);
    }

    @Override
    public String healthCheck() {
        logger.info("EJB Central - Vérification santé des microservices");

        StringBuilder healthStatus = new StringBuilder();
        healthStatus.append("Central Service: OK\n");

        try {
            // Tester CurrentAccount
            List<CompteInfo> comptes = currentAccountClient.getAllComptes();
            healthStatus.append("CurrentAccount: OK (").append(comptes.size()).append(" comptes)\n");
        } catch (Exception e) {
            healthStatus.append("CurrentAccount: DOWN - ").append(e.getMessage()).append("\n");
        }

        try {
            // Tester Loan
            List<PretInfo> prets = loanClient.getPretsByCompte(1);
            healthStatus.append("Loan: OK\n");
        } catch (Exception e) {
            healthStatus.append("Loan: DOWN - ").append(e.getMessage()).append("\n");
        }

        try {
            // Tester Deposit
            List<DepotInfo> depots = depositClient.getDepotsByCompte(1);
            healthStatus.append("Deposit: OK\n");
        } catch (Exception e) {
            healthStatus.append("Deposit: DOWN - ").append(e.getMessage()).append("\n");
        }

        return healthStatus.toString();
    }

    @Override
    public Integer calculerScoreCredit(Integer idCompte) {
        logger.info("EJB Central - Calcul score crédit pour le compte: {}", idCompte);

        try {
            CompteAvecDetails details = getCompteAvecDetails(idCompte);
            if (details == null) {
                return 0;
            }

            // Algorithme simple de calcul de score
            int score = 700; // Score de base

            // Facteur: Solde positif
            if (details.getSoldeNet().doubleValue() > 0) {
                score += 100;
            }

            // Facteur: Pas de prêts
            if (details.getPrets().isEmpty()) {
                score += 150;
            }

            // Facteur: Dépôts existants
            if (!details.getDepots().isEmpty()) {
                score += 50;
            }

            // Limiter à 1000
            score = Math.min(score, 1000);

            logger.debug("Score crédit calculé: {} pour le compte {}", score, idCompte);
            return score;

        } catch (Exception e) {
            logger.error("Erreur lors du calcul du score crédit pour le compte {}", idCompte, e);
            return 0;
        }
    }

    @Override
    public List<Object> getHistoriqueComplet(Integer idCompte) {
        logger.info("EJB Central - Récupération historique complet pour le compte: {}", idCompte);
        // Implémentation simplifiée - à compléter avec les transactions
        return List.of();
    }
}