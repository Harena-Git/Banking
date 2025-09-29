package com.example.banking.demo.controller;

import com.example.banking.demo.entity.Transaction;
import com.example.banking.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping
    public List<Transaction> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return service.save(transaction);
    }
}