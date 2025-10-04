package com.example.banking.currentaccount.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.currentaccount.dao.CompteCourantDAO;
import com.example.banking.currentaccount.dao.TransactionDAO;
import com.example.banking.currentaccount.model.CompteCourant;
import com.example.banking.currentaccount.model.Transaction;
import com.example.banking.currentaccount.ejb.CompteCourantEJB;

public class CompteCourantService {
    private static final Logger logger = LoggerFactory.getLogger(CompteCourantService.class);

    @EJB
    private CompteCouranteEJB CompteCourantEJB;

    private final CompteCourantDAO compteCourantDAO;
    private final TransactionDAO transactionDAO;
    
    public CompteCourantService() {
        this.compteCourantDAO = new CompteCourantDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public List<CompteCourant> getAllComptes() {
        return compteCourantEJB.getAllComptes();
    }

    public Optional<CompteCourant> getCompteById(Integer id) {
        CompteCourant compte = compteCourantEJB.getCompteById(id);
        return Optional.ofNullable(compte);
    }

    public boolean compteExiste(Integer idCompte) {
        return compteCourantEJB.compteExiste(idCompte);
    }
    
    public CompteCourant createCompte(CompteCourant compte) {
        logger.info("Création d'un nouveau compte avec solde: {}", compte.getSoldeCompteCourant());
        
        // Validation
        if (compte.getSoldeCompteCourant() == null || compte.getSoldeCompteCourant().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif");
        }
        
        CompteCourant created = compteCourantDAO.create(compte);
        
        // Créer une transaction initiale si solde > 0
        if (created.getSoldeCompteCourant().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(
                created.getIdCompteCourant(),
                created.getSoldeCompteCourant(),
                "DEPOT",
                "Solde initial"
            );
            transactionDAO.create(transaction);
        }
        
        return created;
    }
    
    public boolean compteExiste(Integer idCompte) {
        return compteCourantDAO.findById(idCompte).isPresent();
    }
    
    public void updateSolde(Integer idCompte, BigDecimal nouveauSolde) {
        logger.info("Mise à jour du solde du compte {}: {}", idCompte, nouveauSolde);
        
        if (nouveauSolde.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le solde ne peut pas être négatif");
        }
        
        compteCourantDAO.updateSolde(idCompte, nouveauSolde);
    }
    
    public boolean deleteCompte(Integer id) {
        logger.info("Suppression du compte: {}", id);
        return compteCourantDAO.delete(id);
    }
    
    public List<Transaction> getTransactionsByCompte(Integer idCompte) {
        logger.debug("Récupération des transactions du compte: {}", idCompte);
        return transactionDAO.findByCompteCourant(idCompte);
    }
}