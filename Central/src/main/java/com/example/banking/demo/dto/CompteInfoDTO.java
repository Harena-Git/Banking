package com.example.banking.demo.dto;

import java.math.BigDecimal;

public class CompteInfoDTO {
    private Integer idCompteCourant;
    private BigDecimal soldeCompteCourant;
    
    public CompteInfoDTO() {}
    
    public CompteInfoDTO(Integer idCompteCourant, BigDecimal soldeCompteCourant) {
        this.idCompteCourant = idCompteCourant;
        this.soldeCompteCourant = soldeCompteCourant;
    }
    
    // Getters and Setters
    public Integer getIdCompteCourant() { return idCompteCourant; }
    public void setIdCompteCourant(Integer idCompteCourant) { this.idCompteCourant = idCompteCourant; }
    
    public BigDecimal getSoldeCompteCourant() { return soldeCompteCourant; }
    public void setSoldeCompteCourant(BigDecimal soldeCompteCourant) { this.soldeCompteCourant = soldeCompteCourant; }
}