package com.example.banking.currentaccount.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompteCourant {
    private Integer idCompteCourant;
    private BigDecimal soldeCompteCourant;
    private LocalDateTime dateCreation;
    private String statut;
    
    // Constructors
    public CompteCourant() {}
    
    public CompteCourant(Integer idCompteCourant, BigDecimal soldeCompteCourant, 
                        LocalDateTime dateCreation, String statut) {
        this.idCompteCourant = idCompteCourant;
        this.soldeCompteCourant = soldeCompteCourant;
        this.dateCreation = dateCreation;
        this.statut = statut;
    }
    
    // Getters and Setters
    public Integer getIdCompteCourant() { return idCompteCourant; }
    public void setIdCompteCourant(Integer idCompteCourant) { this.idCompteCourant = idCompteCourant; }
    
    public BigDecimal getSoldeCompteCourant() { return soldeCompteCourant; }
    public void setSoldeCompteCourant(BigDecimal soldeCompteCourant) { this.soldeCompteCourant = soldeCompteCourant; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    @Override
    public String toString() {
        return String.format("CompteCourant{id=%d, solde=%.2f, statut=%s}", 
            idCompteCourant, soldeCompteCourant, statut);
    }
}