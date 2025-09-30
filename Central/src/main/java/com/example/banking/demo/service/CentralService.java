package com.example.banking.demo.service;

import com.example.banking.demo.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class CentralService {

    private final WebClient webClient;

    // URLs des autres microservices
    private static final String CURRENT_ACCOUNT_URL = "http://localhost:8081";
    private static final String LOAN_URL = "http://localhost:8082";
    private static final String DEPOSIT_URL = "http://localhost:8084";

    @Autowired
    public CentralService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // Consultation du solde d'un compte
    public Mono<CompteInfoDTO> getCompteInfo(Integer idCompte) {
        return webClient.get()
                .uri(CURRENT_ACCOUNT_URL + "/api/compte-courant/{id}", idCompte)
                .retrieve()
                .bodyToMono(CompteInfoDTO.class);
    }

    // Consultation des prêts d'un compte
    public Mono<List<PretInfoDTO>> getPretsByCompte(Integer idCompte) {
        return webClient.get()
                .uri(LOAN_URL + "/api/pret/compte/{idCompte}", idCompte)
                .retrieve()
                .bodyToMono(PretInfoDTO[].class)
                .map(Arrays::asList);
    }

    // Consultation des dépôts d'un compte
    public Mono<List<DepotInfoDTO>> getDepotsByCompte(Integer idCompte) {
        return webClient.get()
                .uri(DEPOSIT_URL + "/api/depot/compte/{idCompteCourant}", idCompte)
                .retrieve()
                .bodyToMono(DepotInfoDTO[].class)
                .map(Arrays::asList);
    }

    // Historique complet d'un compte (transactions + prêts + dépôts)
    public Mono<List<HistoriqueDTO>> getHistoriqueComplet(Integer idCompte) {
        // Implémentation pour récupérer l'historique de toutes les sources
        return Mono.just(List.of()); // À compléter
    }

    // Consultation de tous les comptes
    public Mono<List<CompteInfoDTO>> getAllComptes() {
        return webClient.get()
                .uri(CURRENT_ACCOUNT_URL + "/api/compte-courant")
                .retrieve()
                .bodyToMono(CompteInfoDTO[].class)
                .map(Arrays::asList);
    }
}