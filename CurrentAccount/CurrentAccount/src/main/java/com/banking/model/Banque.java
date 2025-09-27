// src/main/java/com/banking/model/Banque.java
package com.banking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "banque")
public class Banque {
    @Id
    @Column(name = "id_banque")
    private int idBanque;

    @Column(name = "nom_banque", unique = true, nullable = false)
    private String nomBanque;

    @Column(name = "localisation")
    private String localisation;

    // Getters and Setters
    public int getIdBanque() {
        return idBanque;
    }

    public void setIdBanque(int idBanque) {
        this.idBanque = idBanque;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}