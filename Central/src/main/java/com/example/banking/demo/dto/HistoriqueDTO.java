package com.example.banking.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoriqueDTO {
    private Integer idTransaction;
    private LocalDate dateTransaction;
    private BigDecimal montantTransaction;
    private String typeTransaction;
    private String source; // "COMPTE", "PRET", "DEPOT"
    
    public HistoriqueDTO() {}
    
    // Getters and Setters
    public Integer getIdTransaction() { return idTransaction; }
    public void setIdTransaction(Integer idTransaction) { this.idTransaction = idTransaction; }
    
    public LocalDate getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDate dateTransaction) { this.dateTransaction = dateTransaction; }
    
    public BigDecimal getMontantTransaction() { return montantTransaction; }
    public void setMontantTransaction(BigDecimal montantTransaction) { this.montantTransaction = montantTransaction; }
    
    public String getTypeTransaction() { return typeTransaction; }
    public void setTypeTransaction(String typeTransaction) { this.typeTransaction = typeTransaction; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}