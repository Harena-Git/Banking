package com.example.banking.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking.demo.entity.PretValues;
import com.example.banking.demo.service.PretValuesService;

@RestController
@RequestMapping("/api/pret-values")
public class PretValuesController {
    @Autowired
    private PretValuesService service;

    @GetMapping
    public List<PretValues> getAll() {
        return service.findAll();
    }

    @PostMapping
    public PretValues create(@RequestBody PretValues pretValues) {
        return service.save(pretValues);
    }
}