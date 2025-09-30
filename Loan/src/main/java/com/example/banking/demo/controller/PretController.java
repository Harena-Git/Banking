package com.example.banking.demo.controller;

import com.example.banking.demo.entity.Pret;
import com.example.banking.demo.service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prets")
public class PretController {
    @Autowired
    private PretService service;

    @GetMapping
    public List<Pret> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pret> getById(@PathVariable Integer id) {
        Optional<Pret> pret = service.findById(id);
        return pret.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pret create(@RequestBody Pret pret) {
        return service.save(pret);
    }

    // Endpoint pour calcul prêt (à appeler depuis Central via EJB/Wildfly)
    @PostMapping("/calcul")
    public ResponseEntity<String> calcul(@RequestParam Integer idPret, @RequestParam BigDecimal montant) {
        service.calculerPret(idPret, montant);
        return ResponseEntity.ok("Prêt calculé");
    }
}