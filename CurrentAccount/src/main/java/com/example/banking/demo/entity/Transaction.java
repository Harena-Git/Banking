package com.example.banking.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransaction;

    @Column
    private LocalDate dateTransaction;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal montantTransaction;

    @Column(length = 50)
    private String typeTransaction;

    @ManyToOne
    @JoinColumn(name = "id_compte_courant")
    private CompteCourant compteCourant;

    // Getters et Setters
    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public BigDecimal getMontantTransaction() {
        return montantTransaction;
    }

    public void setMontantTransaction(BigDecimal montantTransaction) {
        this.montantTransaction = montantTransaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public CompteCourant getCompteCourant() {
        return compteCourant;
    }

    public void setCompteCourant(CompteCourant compteCourant) {
        this.compteCourant = compteCourant;
    }
}