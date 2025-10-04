package com.example.banking.currentaccount.ejb;

import com.example.banking.currentaccount.model.Transaction;
import jakarta.ejb.Remote;
import java.util.List;

/**
 * Interface EJB Remote pour la gestion des transactions
 */
@Remote
public interface TransactionEJBRemote {

    /**
     * Récupère toutes les transactions d'un compte
     * @param idCompte Identifiant du compte
     * @return Liste des transactions
     */
    List<Transaction> getTransactionsByCompte(Integer idCompte);

    /**
     * Crée une nouvelle transaction
     * @param transaction La transaction à créer
     * @return La transaction créée avec son ID
     */
    Transaction createTransaction(Transaction transaction);

    /**
     * Récupère une transaction par son ID
     * @param id Identifiant de la transaction
     * @return La transaction trouvée ou null
     */
    Transaction getTransactionById(Integer id);
}