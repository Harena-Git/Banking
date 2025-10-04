package com.example.banking.currentaccount.ejb;

import com.example.banking.currentaccount.dao.TransactionDAO;
import com.example.banking.currentaccount.model.Transaction;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * EJB Stateless pour la gestion des transactions
 */
@Stateless
public class TransactionEJB implements TransactionEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(TransactionEJB.class);

    private final TransactionDAO transactionDAO;

    public TransactionEJB() {
        this.transactionDAO = new TransactionDAO();
        logger.info("EJB TransactionEJB initialisé");
    }

    @Override
    public List<Transaction> getTransactionsByCompte(Integer idCompte) {
        logger.info("EJB - Récupération transactions du compte: {}", idCompte);
        return transactionDAO.findByCompteCourant(idCompte);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        logger.info("EJB - Création d'une nouvelle transaction");
        return transactionDAO.create(transaction);
    }

    @Override
    public Transaction getTransactionById(Integer id) {
        logger.debug("EJB - Recherche transaction: {}", id);
        return transactionDAO.findById(id);
    }
}