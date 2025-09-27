// Banking/CurrentAccount/src/main/java/com/banking/model/Transaction.java
package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column(name = "id_transaction")
    private int idTransaction;

    @Column(name = "id_compte", nullable = false)
    private int idCompte;

    @Column(name = "date_transaction", nullable = false)
    private LocalDate dateTransaction;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "type_transaction")
    private String typeTransaction;

    @ManyToOne
    @JoinColumn(name = "id_compte", insertable = false, updatable = false)
    private Compte compte;

    // Getters et Setters
    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}