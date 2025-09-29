package com.example.banking.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.demo.entity.PretValues;
import com.example.banking.demo.repository.PretValuesRepository;

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