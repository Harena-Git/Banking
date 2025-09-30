package com.example.banking.demo.dto;

import java.math.BigDecimal;

public class PretInfoDTO {
    private Integer idPret;
    private BigDecimal montantPret;
    private String typePret;
    private BigDecimal pourcentage;
    private Integer duree;
    
    public PretInfoDTO() {}
    
    // Getters and Setters
    public Integer getIdPret() { return idPret; }
    public void setIdPret(Integer idPret) { this.idPret = idPret; }
    
    public BigDecimal getMontantPret() { return montantPret; }
    public void setMontantPret(BigDecimal montantPret) { this.montantPret = montantPret; }
    
    public String getTypePret() { return typePret; }
    public void setTypePret(String typePret) { this.typePret = typePret; }
    
    public BigDecimal getPourcentage() { return pourcentage; }
    public void setPourcentage(BigDecimal pourcentage) { this.pourcentage = pourcentage; }
    
    public Integer getDuree() { return duree; }
    public void setDuree(Integer duree) { this.duree = duree; }
}