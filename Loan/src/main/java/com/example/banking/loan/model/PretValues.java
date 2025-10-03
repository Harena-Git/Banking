package com.example.banking.loan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PretValues {
    private Integer idPretValues;
    private BigDecimal pourcentagePretValues;
    private Integer dureePretValues;
    private Integer idPret;
    private String typeTaux;
    private LocalDateTime dateCreation;
    
    // Constructors
    public PretValues() {}
    
    public PretValues(BigDecimal pourcentagePretValues, Integer dureePretValues, Integer idPret) {
        this.pourcentagePretValues = pourcentagePretValues;
        this.dureePretValues = dureePretValues;
        this.idPret = idPret;
        this.typeTaux = "FIXE";
        this.dateCreation = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getIdPretValues() { return idPretValues; }
    public void setIdPretValues(Integer idPretValues) { this.idPretValues = idPretValues; }
    
    public BigDecimal getPourcentagePretValues() { return pourcentagePretValues; }
    public void setPourcentagePretValues(BigDecimal pourcentagePretValues) { this.pourcentagePretValues = pourcentagePretValues; }
    
    public Integer getDureePretValues() { return dureePretValues; }
    public void setDureePretValues(Integer dureePretValues) { this.dureePretValues = dureePretValues; }
    
    public Integer getIdPret() { return idPret; }
    public void setIdPret(Integer idPret) { this.idPret = idPret; }
    
    public String getTypeTaux() { return typeTaux; }
    public void setTypeTaux(String typeTaux) { this.typeTaux = typeTaux; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    @Override
    public String toString() {
        return String.format("PretValues{id=%d, taux=%.2f%%, duree=%d mois, pret=%d}", 
            idPretValues, pourcentagePretValues, dureePretValues, idPret);
    }
}