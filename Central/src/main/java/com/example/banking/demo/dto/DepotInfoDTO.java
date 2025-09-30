package com.example.banking.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepotInfoDTO {
    private Integer idDepot;
    private LocalDate dateDepot;
    private BigDecimal montantDepot;
    private String typeDepot;
    private BigDecimal pourcentageInteret;
    private Integer dureeInteret;
    
    public DepotInfoDTO() {}
    
    // Getters and Setters
    public Integer getIdDepot() { return idDepot; }
    public void setIdDepot(Integer idDepot) { this.idDepot = idDepot; }
    
    public LocalDate getDateDepot() { return dateDepot; }
    public void setDateDepot(LocalDate dateDepot) { this.dateDepot = dateDepot; }
    
    public BigDecimal getMontantDepot() { return montantDepot; }
    public void setMontantDepot(BigDecimal montantDepot) { this.montantDepot = montantDepot; }
    
    public String getTypeDepot() { return typeDepot; }
    public void setTypeDepot(String typeDepot) { this.typeDepot = typeDepot; }
    
    public BigDecimal getPourcentageInteret() { return pourcentageInteret; }
    public void setPourcentageInteret(BigDecimal pourcentageInteret) { this.pourcentageInteret = pourcentageInteret; }
    
    public Integer getDureeInteret() { return dureeInteret; }
    public void setDureeInteret(Integer dureeInteret) { this.dureeInteret = dureeInteret; }
}