package com.example.banking.demo.controller;

import com.example.banking.demo.dto.*;
import com.example.banking.demo.service.CentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/central")
public class CentralController {

    @Autowired
    private CentralService centralService;

    // Consultation du solde d'un compte
    @GetMapping("/compte/{idCompte}")
    public Mono<ResponseEntity<CompteInfoDTO>> getCompteInfo(@PathVariable Integer idCompte) {
        return centralService.getCompteInfo(idCompte)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Consultation des prêts d'un compte
    @GetMapping("/compte/{idCompte}/prets")
    public Mono<ResponseEntity<List<PretInfoDTO>>> getPretsByCompte(@PathVariable Integer idCompte) {
        return centralService.getPretsByCompte(idCompte)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Consultation des dépôts d'un compte
    @GetMapping("/compte/{idCompte}/depots")
    public Mono<ResponseEntity<List<DepotInfoDTO>>> getDepotsByCompte(@PathVariable Integer idCompte) {
        return centralService.getDepotsByCompte(idCompte)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Historique complet d'un compte
    @GetMapping("/compte/{idCompte}/historique")
    public Mono<ResponseEntity<List<HistoriqueDTO>>> getHistoriqueComplet(@PathVariable Integer idCompte) {
        return centralService.getHistoriqueComplet(idCompte)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Consultation de tous les comptes
    @GetMapping("/comptes")
    public Mono<ResponseEntity<List<CompteInfoDTO>>> getAllComptes() {
        return centralService.getAllComptes()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Endpoint de santé pour vérifier la connectivité avec les autres services
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Central Service is running");
    }
}