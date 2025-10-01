package com.example.banking.loan.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pret {
    private Integer idPret;
    private BigDecimal montantPret;
    private String typePret;
    private Integer idCompteCourant;
    private LocalDateTime dateCreation;
    private String statut;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    // Constructors
    public Pret() {}
    
    public Pret(BigDecimal montantPret, String typePret, Integer idCompteCourant) {
        this.montantPret = montantPret;
        this.typePret = typePret;
        this.idCompteCourant = idCompteCourant;
        this.dateCreation = LocalDateTime.now();
        this.statut = "EN_ATTENTE";
    }
    
    // Getters and Setters
    public Integer getIdPret() { return idPret; }
    public void setIdPret(Integer idPret) { this.idPret = idPret; }
    
    public BigDecimal getMontantPret() { return montantPret; }
    public void setMontantPret(BigDecimal montantPret) { this.montantPret = montantPret; }
    
    public String getTypePret() { return typePret; }
    public void setTypePret(String typePret) { this.typePret = typePret; }
    
    public Integer getIdCompteCourant() { return idCompteCourant; }
    public void setIdCompteCourant(Integer idCompteCourant) { this.idCompteCourant = idCompteCourant; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    @Override
    public String toString() {
        return String.format("Pret{id=%d, montant=%.2f, type=%s, compte=%d, statut=%s}", 
            idPret, montantPret, typePret, idCompteCourant, statut);
    }
}