package com.example.banking.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.demo.entity.Pret;
import com.example.banking.demo.repository.PretRepository;

@Service
public class PretService {
    @Autowired
    private PretRepository repository;

    public List<Pret> findAll() {
        return repository.findAll();
    }

    public Optional<Pret> findById(Integer id) {
        return repository.findById(id);
    }

    public Pret save(Pret pret) {
        return repository.save(pret);
    }

    // Méthode pour calculer prêt (à étendre pour inter-microservices via EJB)
    public void calculerPret(Integer idPret, BigDecimal montant) {
        // Logique basique (placeholder pour intégration avec Central)
    }
}