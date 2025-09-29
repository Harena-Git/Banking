package com.example.banking.demo.controller;

import com.example.banking.demo.entity.CompteCourant;
import com.example.banking.demo.service.CompteCourantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes-courants")
public class CompteCourantController {
    @Autowired
    private CompteCourantService service;

    @GetMapping
    public List<CompteCourant> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteCourant> getById(@PathVariable Integer id) {
        Optional<CompteCourant> compte = service.findById(id);
        return compte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompteCourant create(@RequestBody CompteCourant compte) {
        return service.save(compte);
    }

    // Endpoint pour virement (à appeler depuis Central via EJB/Wildfly)
    @PostMapping("/virement")
    public ResponseEntity<String> virement(@RequestParam Integer idSource, @RequestParam Integer idDest, @RequestParam BigDecimal montant) {
        service.effectuerVirement(idSource, idDest, montant);
        return ResponseEntity.ok("Virement effectué");
    }
}