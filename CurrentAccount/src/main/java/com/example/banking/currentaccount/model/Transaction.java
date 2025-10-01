package com.example.banking.currentaccount.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Integer idTransaction;
    private LocalDateTime dateTransaction;
    private BigDecimal montantTransaction;
    private String typeTransaction;
    private Integer idCompteCourant;
    private String description;
    private Integer compteDestinataire;
    
    // Constructors
    public Transaction() {}
    
    public Transaction(Integer idCompteCourant, BigDecimal montantTransaction, 
                      String typeTransaction, String description) {
        this.idCompteCourant = idCompteCourant;
        this.montantTransaction = montantTransaction;
        this.typeTransaction = typeTransaction;
        this.description = description;
        this.dateTransaction = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getIdTransaction() { return idTransaction; }
    public void setIdTransaction(Integer idTransaction) { this.idTransaction = idTransaction; }
    
    public LocalDateTime getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDateTime dateTransaction) { this.dateTransaction = dateTransaction; }
    
    public BigDecimal getMontantTransaction() { return montantTransaction; }
    public void setMontantTransaction(BigDecimal montantTransaction) { this.montantTransaction = montantTransaction; }
    
    public String getTypeTransaction() { return typeTransaction; }
    public void setTypeTransaction(String typeTransaction) { this.typeTransaction = typeTransaction; }
    
    public Integer getIdCompteCourant() { return idCompteCourant; }
    public void setIdCompteCourant(Integer idCompteCourant) { this.idCompteCourant = idCompteCourant; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getCompteDestinataire() { return compteDestinataire; }
    public void setCompteDestinataire(Integer compteDestinataire) { this.compteDestinataire = compteDestinataire; }
    
    @Override
    public String toString() {
        return String.format("Transaction{id=%d, montant=%.2f, type=%s, compte=%d}", 
            idTransaction, montantTransaction, typeTransaction, idCompteCourant);
    }
}