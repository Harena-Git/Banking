package com.example.banking.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pret_values")
public class PretValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPretValues;

    @Column(precision = 15, scale = 2)
    private BigDecimal pourcentagePretValues;

    private Integer dureePretValues;

    @ManyToOne
    @JoinColumn(name = "id_pret")
    private Pret pret;

    // Getters et Setters
    public Integer getIdPretValues() {
        return idPretValues;
    }

    public void setIdPretValues(Integer idPretValues) {
        this.idPretValues = idPretValues;
    }

    public BigDecimal getPourcentagePretValues() {
        return pourcentagePretValues;
    }

    public void setPourcentagePretValues(BigDecimal pourcentagePretValues) {
        this.pourcentagePretValues = pourcentagePretValues;
    }

    public Integer getDureePretValues() {
        return dureePretValues;
    }

    public void setDureePretValues(Integer dureePretValues) {
        this.dureePretValues = dureePretValues;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }
}