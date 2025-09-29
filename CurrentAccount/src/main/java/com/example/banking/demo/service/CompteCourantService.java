package com.example.banking.demo.service;

import com.example.banking.demo.entity.CompteCourant;
import com.example.banking.demo.repository.CompteCourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CompteCourantService {
    @Autowired
    private CompteCourantRepository repository;

    public List<CompteCourant> findAll() {
        return repository.findAll();
    }

    public Optional<CompteCourant> findById(Integer id) {
        return repository.findById(id);
    }

    public CompteCourant save(CompteCourant compte) {
        return repository.save(compte);
    }

    // Méthode pour virement (à étendre pour inter-microservices)
    public void effectuerVirement(Integer idSource, Integer idDest, BigDecimal montant) {
        // Logique basique : déduire de source, ajouter à dest (gérer via EJB pour inter-services)
        // Pour l'instant, placeholder
    }
}