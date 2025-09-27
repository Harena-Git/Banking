// src/main/java/com/banking/ejb/CurrentAccountBean.java
package com.banking.ejb;

import com.banking.model.Compte;
import com.banking.model.Transaction;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless
@Remote(CurrentAccountRemote.class)
public class CurrentAccountBean implements CurrentAccountRemote {

    @PersistenceContext(unitName = "bankingPU")
    private EntityManager em;

    @Override
    public BigDecimal getSolde(int idCompte) {
        Compte compte = em.find(Compte.class, idCompte);
        if (compte != null && "courant".equals(compte.getTypeCompte())) {
            return compte.getSolde();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void depot(int idCompte, BigDecimal montant) {
        Compte compte = em.find(Compte.class, idCompte);
        if (compte != null && "courant".equals(compte.getTypeCompte())) {
            compte.setSolde(compte.getSolde().add(montant));
            em.merge(compte);

            Transaction transaction = new Transaction();
            transaction.setIdCompte(idCompte);
            transaction.setDateTransaction(new Date());
            transaction.setMontant(montant);
            transaction.setTypeTransaction("depot");
            em.persist(transaction);
        }
    }

    @Override
    public void retrait(int idCompte, BigDecimal montant) {
        Compte compte = em.find(Compte.class, idCompte);
        if (compte != null && "courant".equals(compte.getTypeCompte()) && compte.getSolde().compareTo(montant) >= 0) {
            compte.setSolde(compte.getSolde().subtract(montant));
            em.merge(compte);

            Transaction transaction = new Transaction();
            transaction.setIdCompte(idCompte);
            transaction.setDateTransaction(new Date());
            transaction.setMontant(montant.negate());
            transaction.setTypeTransaction("retrait");
            em.persist(transaction);
        }
    }

    @Override
    public void virement(int idCompteSource, int idCompteDest, BigDecimal montant) {
        retrait(idCompteSource, montant);
        depot(idCompteDest, montant);

        // Enregistrer la transaction de virement pour source
        Transaction transactionSource = new Transaction();
        transactionSource.setIdCompte(idCompteSource);
        transactionSource.setDateTransaction(new Date());
        transactionSource.setMontant(montant.negate());
        transactionSource.setTypeTransaction("virement");
        em.persist(transactionSource);

        // Enregistrer pour destination
        Transaction transactionDest = new Transaction();
        transactionDest.setIdCompte(idCompteDest);
        transactionDest.setDateTransaction(new Date());
        transactionDest.setMontant(montant);
        transactionDest.setTypeTransaction("virement");
        em.persist(transactionDest);
    }

    @Override
    public List<Transaction> getHistoriqueTransactions(int idCompte) {
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.idCompte = :idCompte ORDER BY t.dateTransaction DESC");
        query.setParameter("idCompte", idCompte);
        return query.getResultList();
    }
}