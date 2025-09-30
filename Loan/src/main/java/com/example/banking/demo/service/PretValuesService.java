package com.example.banking.demo.service;

import com.example.banking.demo.entity.PretValues;
import com.example.banking.demo.repository.PretValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PretValuesService {
    @Autowired
    private PretValuesRepository repository;

    public List<PretValues> findAll() {
        return repository.findAll();
    }

    public PretValues save(PretValues pretValues) {
        return repository.save(pretValues);
    }

    // Ajouter logique pour taux et durées
}