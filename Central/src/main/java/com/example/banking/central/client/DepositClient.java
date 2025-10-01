package com.example.banking.central.client;

import com.example.banking.central.model.DepotInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DepositClient {
    private static final Logger logger = LoggerFactory.getLogger(DepositClient.class);
    private final String baseUrl;
    private final ObjectMapper objectMapper;
    
    public DepositClient(Properties config) {
        this.baseUrl = config.getProperty("deposit.url", "http://localhost:8084/depositaccount");
        this.objectMapper = new ObjectMapper();
    }
    
    public List<DepotInfo> getDepotsByCompte(Integer idCompte) {
        String url = baseUrl + "/api/depot/compte/" + idCompte;
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                
                if (response.getCode() == 200) {
                    DepotInfo[] depots = objectMapper.readValue(jsonResponse, DepotInfo[].class);
                    return Arrays.asList(depots);
                } else {
                    logger.warn("Aucun dépôt trouvé pour le compte {} - Status: {}", idCompte, response.getCode());
                    return List.of();
                }
            }
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel à Deposit pour les dépôts du compte {}", idCompte, e);
            return List.of();
        }
    }
}