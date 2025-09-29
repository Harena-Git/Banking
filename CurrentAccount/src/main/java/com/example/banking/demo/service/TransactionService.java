package com.example.banking.demo.service;

import com.example.banking.demo.entity.Transaction;
import com.example.banking.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    // Ajouter logique pour transactions (ex: crédit/débit affectant solde)
}