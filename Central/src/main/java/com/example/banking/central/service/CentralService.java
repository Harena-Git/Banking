package com.example.banking.central.service;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.central.client.CurrentAccountClient;
import com.example.banking.central.client.DepositClient;
import com.example.banking.central.client.LoanClient;
import com.example.banking.central.model.CompteAvecDetails;
import com.example.banking.central.model.CompteInfo;
import com.example.banking.central.model.DepotInfo;
import com.example.banking.central.model.PretInfo;

public class CentralService {
    private static final Logger logger = LoggerFactory.getLogger(CentralService.class);
    
    private final CurrentAccountClient currentAccountClient;
    private final LoanClient loanClient;
    private final DepositClient depositClient;
    
    public CentralService(Properties config) {
        this.currentAccountClient = new CurrentAccountClient(config);
        this.loanClient = new LoanClient(config);
        this.depositClient = new DepositClient(config);
    }
    
    public CompteAvecDetails getCompteAvecDetails(Integer idCompte) {
        logger.info("Récupération des détails complets pour le compte: {}", idCompte);
        
        try {
            // 1. Récupérer les infos du compte courant
            CompteInfo compte = currentAccountClient.getCompteById(idCompte);
            if (compte == null) {
                logger.warn("Compte non trouvé: {}", idCompte);
                return null;
            }
            
            // 2. Récupérer les prêts du compte (en parallèle si possible)
            List<PretInfo> prets = loanClient.getPretsByCompte(idCompte);
            logger.debug("Trouvé {} prêts pour le compte {}", prets.size(), idCompte);
            
            // 3. Récupérer les dépôts du compte
            List<DepotInfo> depots = depositClient.getDepotsByCompte(idCompte);
            logger.debug("Trouvé {} dépôts pour le compte {}", depots.size(), idCompte);
            
            // 4. Agrégation des données
            CompteAvecDetails compteAvecDetails = new CompteAvecDetails(compte, prets, depots);
            logger.info("Détails complets générés pour le compte: {}", idCompte);
            
            return compteAvecDetails;
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'agrégation des données pour le compte {}", idCompte, e);
            throw new RuntimeException("Erreur lors de la récupération des données agrégées", e);
        }
    }
    
    public List<CompteInfo> getAllComptes() {
        logger.info("Récupération de la liste de tous les comptes");
        
        try {
            List<CompteInfo> comptes = currentAccountClient.getAllComptes();
            logger.info("Trouvé {} comptes au total", comptes.size());
            return comptes;
            
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de tous les comptes", e);
            throw new RuntimeException("Erreur lors de la récupération des comptes", e);
        }
    }
    
    public CompteInfo getCompteInfo(Integer idCompte) {
        logger.debug("Récupération des informations de base du compte: {}", idCompte);
        
        try {
            CompteInfo compte = currentAccountClient.getCompteById(idCompte);
            if (compte != null) {
                logger.debug("Compte trouvé: {}", compte.getIdCompteCourant());
            } else {
                logger.debug("Compte non trouvé: {}", idCompte);
            }
            return compte;
            
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du compte {}", idCompte, e);
            throw new RuntimeException("Erreur lors de la récupération du compte", e);
        }
    }
    
    public List<PretInfo> getPretsByCompte(Integer idCompte) {
        logger.debug("Récupération des prêts du compte: {}", idCompte);
        
        try {
            List<PretInfo> prets = loanClient.getPretsByCompte(idCompte);
            logger.debug("Trouvé {} prêts pour le compte {}", prets.size(), idCompte);
            return prets;
            
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des prêts du compte {}", idCompte, e);
            throw new RuntimeException("Erreur lors de la récupération des prêts", e);
        }
    }
    
    public List<DepotInfo> getDepotsByCompte(Integer idCompte) {
        logger.debug("Récupération des dépôts du compte: {}", idCompte);
        
        try {
            List<DepotInfo> depots = depositClient.getDepotsByCompte(idCompte);
            logger.debug("Trouvé {} dépôts pour le compte {}", depots.size(), idCompte);
            return depots;
            
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des dépôts du compte {}", idCompte, e);
            throw new RuntimeException("Erreur lors de la récupération des dépôts", e);
        }
    }
    
    public String healthCheck() {
        logger.info("Vérification de la santé des microservices");
        
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
            // Tester Loan (en essayant de récupérer les prêts d'un compte fictif)
            List<PretInfo> prets = loanClient.getPretsByCompte(999999);
            healthStatus.append("Loan: OK\n");
        } catch (Exception e) {
            healthStatus.append("Loan: DOWN - ").append(e.getMessage()).append("\n");
        }
        
        try {
            // Tester Deposit (en essayant de récupérer les dépôts d'un compte fictif)
            List<DepotInfo> depots = depositClient.getDepotsByCompte(999999);
            healthStatus.append("Deposit: OK\n");
        } catch (Exception e) {
            healthStatus.append("Deposit: DOWN - ").append(e.getMessage()).append("\n");
        }
        
        return healthStatus.toString();
    }
}