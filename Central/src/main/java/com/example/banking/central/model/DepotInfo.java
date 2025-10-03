package com.example.banking.central.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepotInfo {
    private Integer idDepot;
    private LocalDateTime dateDepot;
    private BigDecimal montantDepot;
    private String typeDepot;
    private BigDecimal pourcentageInteret;
    private Integer dureeInteret;
    private Integer idCompteCourant;
    
    public DepotInfo() {}
    
    // Getters and Setters
    public Integer getIdDepot() { return idDepot; }
    public void setIdDepot(Integer idDepot) { this.idDepot = idDepot; }
    
    public LocalDateTime getDateDepot() { return dateDepot; }
    public void setDateDepot(LocalDateTime dateDepot) { this.dateDepot = dateDepot; }
    
    public BigDecimal getMontantDepot() { return montantDepot; }
    public void setMontantDepot(BigDecimal montantDepot) { this.montantDepot = montantDepot; }
    
    public String getTypeDepot() { return typeDepot; }
    public void setTypeDepot(String typeDepot) { this.typeDepot = typeDepot; }
    
    public BigDecimal getPourcentageInteret() { return pourcentageInteret; }
    public void setPourcentageInteret(BigDecimal pourcentageInteret) { this.pourcentageInteret = pourcentageInteret; }
    
    public Integer getDureeInteret() { return dureeInteret; }
    public void setDureeInteret(Integer dureeInteret) { this.dureeInteret = dureeInteret; }
    
    public Integer getIdCompteCourant() { return idCompteCourant; }
    public void setIdCompteCourant(Integer idCompteCourant) { this.idCompteCourant = idCompteCourant; }
}