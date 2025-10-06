package com.example.banking.central.client;

import com.example.banking.central.model.DepotInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepositRestClient {
    private static final Logger logger = LoggerFactory.getLogger(DepositRestClient.class);
    private final String baseUrl;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public DepositRestClient(Properties config) {
        this.baseUrl = config.getProperty("deposit.url", "http://localhost:8084");
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newHttpClient();
        logger.info("Client REST Deposit initialisé avec URL: {}", baseUrl);
    }

    public List<DepotInfo> getDepotsByCompte(Integer idCompte) {
        String url = baseUrl + "/api/depot/compte/" + idCompte;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Désérialisation de la réponse
                DepotInfo[] depots = objectMapper.readValue(response.body(), DepotInfo[].class);
                logger.debug("Récupéré {} dépôts pour le compte {} via REST", depots.length, idCompte);
                return List.of(depots);
            } else {
                logger.warn("Aucun dépôt trouvé pour le compte {} - Status: {}", idCompte, response.statusCode());
                return List.of();
            }

        } catch (Exception e) {
            logger.error("Erreur lors de l'appel REST pour les dépôts du compte {}", idCompte, e);
            return List.of();
        }
    }

    public String healthCheck() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/health"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 ? "OK" : "DOWN";

        } catch (Exception e) {
            logger.error("Erreur lors du health check Deposit", e);
            return "DOWN";
        }
    }
}