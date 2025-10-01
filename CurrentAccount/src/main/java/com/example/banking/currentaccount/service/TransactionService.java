package com.example.banking.currentaccount.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.currentaccount.dao.CompteCourantDAO;
import com.example.banking.currentaccount.dao.TransactionDAO;
import com.example.banking.currentaccount.model.Transaction;

public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    
    private final TransactionDAO transactionDAO;
    private final CompteCourantDAO compteCourantDAO;
    
    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
        this.compteCourantDAO = new CompteCourantDAO();
    }
    
    public List<Transaction> getAllTransactions() {
        logger.debug("Récupération de toutes les transactions");
        return transactionDAO.findAll();
    }
    
    public List<Transaction> getTransactionsByCompte(Integer idCompte) {
        logger.debug("Récupération des transactions du compte: {}", idCompte);
        return transactionDAO.findByCompteCourant(idCompte);
    }
    
    public Transaction createTransaction(Transaction transaction) {
        logger.info("Création d'une nouvelle transaction: {}", transaction);
        
        // Validation
        if (transaction.getIdCompteCourant() == null) {
            throw new IllegalArgumentException("L'ID du compte est requis");
        }
        
        if (transaction.getMontantTransaction() == null || transaction.getMontantTransaction().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Le montant de la transaction est invalide");
        }
        
        if (transaction.getTypeTransaction() == null || transaction.getTypeTransaction().trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de transaction est requis");
        }
        
        // Vérifier que le compte existe
        if (!compteCourantDAO.findById(transaction.getIdCompteCourant()).isPresent()) {
            throw new IllegalArgumentException("Le compte n'existe pas: " + transaction.getIdCompteCourant());
        }
        
        // Pour les virements, vérifier le compte destinataire
        if ("VIREMENT".equals(transaction.getTypeTransaction()) && transaction.getCompteDestinataire() != null) {
            if (!compteCourantDAO.findById(transaction.getCompteDestinataire()).isPresent()) {
                throw new IllegalArgumentException("Le compte destinataire n'existe pas: " + transaction.getCompteDestinataire());
            }
        }
        
        return transactionDAO.create(transaction);
    }
    
    public Transaction getTransactionById(Integer id) {
        logger.debug("Recherche de la transaction avec ID: {}", id);
        return transactionDAO.findById(id);
    }
}