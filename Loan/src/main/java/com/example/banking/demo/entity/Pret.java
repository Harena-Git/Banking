package com.example.banking.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pret")
public class Pret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPret;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantPret;

    @Column(length = 50)
    private String typePret;

    // Getters et Setters
    public Integer getIdPret() {
        return idPret;
    }

    public void setIdPret(Integer idPret) {
        this.idPret = idPret;
    }

    public BigDecimal getMontantPret() {
        return montantPret;
    }

    public void setMontantPret(BigDecimal montantPret) {
        this.montantPret = montantPret;
    }

    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePret) {
        this.typePret = typePret;
    }
}