package com.example.banking.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "compte_courant")
public class CompteCourant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompteCourant;

    @Column(precision = 15, scale = 2)
    private BigDecimal soldeCompteCourant;

    // Getters et Setters
    public Integer getIdCompteCourant() {
        return idCompteCourant;
    }

    public void setIdCompteCourant(Integer idCompteCourant) {
        this.idCompteCourant = idCompteCourant;
    }

    public BigDecimal getSoldeCompteCourant() {
        return soldeCompteCourant;
    }

    public void setSoldeCompteCourant(BigDecimal soldeCompteCourant) {
        this.soldeCompteCourant = soldeCompteCourant;
    }
}