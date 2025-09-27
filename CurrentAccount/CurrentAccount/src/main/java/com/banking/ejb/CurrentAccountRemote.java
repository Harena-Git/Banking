// Banking/CurrentAccount/src/main/java/com/banking/ejb/CurrentAccountRemote.java
package com.banking.ejb;

import java.math.BigDecimal;
import java.util.List;

import com.banking.model.Transaction;

public interface CurrentAccountRemote {
    BigDecimal getSolde(int idCompte);
    void depot(int idCompte, BigDecimal montant);
    void retrait(int idCompte, BigDecimal montant);
    void virement(int idCompteSource, int idCompteDest, BigDecimal montant);
    List<Transaction> getHistoriqueTransactions(int idCompte);
}