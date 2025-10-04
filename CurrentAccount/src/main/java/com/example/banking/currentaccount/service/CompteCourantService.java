package com.example.banking.currentaccount.service;

import com.example.banking.currentaccount.ejb.CompteCourantEJB;
import com.example.banking.currentaccount.ejb.TransactionEJB;
import com.example.banking.currentaccount.model.CompteCourant;
import com.example.banking.currentaccount.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service qui utilise maintenant les EJB pour la logique métier
 * Les EJB sont injectés automatiquement par le conteneur
 */
public class CompteCourantService {
    private static final Logger logger = LoggerFactory.getLogger(CompteCourantService.class);

    // Injection des EJB
    @EJB
    private CompteCourantEJB compteCourantEJB;

    @EJB
    private TransactionEJB transactionEJB;

    public List<CompteCourant> getAllComptes() {
        logger.debug("Service - Récupération de tous les comptes via EJB");
        return compteCourantEJB.getAllComptes();
    }

    public Optional<CompteCourant> getCompteById(Integer id) {
        logger.debug("Service - Recherche du compte {} via EJB", id);
        CompteCourant compte = compteCourantEJB.getCompteById(id);
        return Optional.ofNullable(compte);
    }

    public CompteCourant createCompte(CompteCourant compte) {
        logger.info("Service - Création d'un compte via EJB");

        // Validation
        if (compte.getSoldeCompteCourant() == null || compte.getSoldeCompteCourant().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif");
        }

        CompteCourant created = compteCourantEJB.createCompte(compte);

        // Créer une transaction initiale si solde > 0
        if (created.getSoldeCompteCourant().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(
                    created.getIdCompteCourant(),
                    created.getSoldeCompteCourant(),
                    "DEPOT",
                    "Solde initial"
            );
            transactionEJB.createTransaction(transaction);
        }

        return created;
    }

    public boolean compteExiste(Integer idCompte) {
        return compteCourantEJB.compteExiste(idCompte);
    }

    public void updateSolde(Integer idCompte, BigDecimal nouveauSolde) {
        logger.info("Service - Mise à jour solde compte {}: {}", idCompte, nouveauSolde);

        if (nouveauSolde.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le solde ne peut pas être négatif");
        }

        // Conversion BigDecimal to Double pour l'EJB
        compteCourantEJB.updateSolde(idCompte, nouveauSolde.doubleValue());
    }

    public boolean deleteCompte(Integer id) {
        logger.info("Service - Suppression du compte: {}", id);
        // Implémentation de la suppression (marquer comme inactif)
        // Cette méthode n'est pas dans l'EJB remote pour l'instant
        return false;
    }

    public List<Transaction> getTransactionsByCompte(Integer idCompte) {
        logger.debug("Service - Récupération transactions compte {} via EJB", idCompte);
        return transactionEJB.getTransactionsByCompte(idCompte);
    }
}