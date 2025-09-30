package com.example.banking.demo.controller;

import com.example.banking.demo.entity.PretValues;
import com.example.banking.demo.service.PretValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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