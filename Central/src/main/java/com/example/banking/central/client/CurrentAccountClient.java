package com.example.banking.central.client;

import com.example.banking.central.model.CompteInfo;
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

public class CurrentAccountClient {
    private static final Logger logger = LoggerFactory.getLogger(CurrentAccountClient.class);
    private final String baseUrl;
    private final ObjectMapper objectMapper;
    
    public CurrentAccountClient(Properties config) {
        this.baseUrl = config.getProperty("currentaccount.url", "http://localhost:8081/currentaccount");
        this.objectMapper = new ObjectMapper();
    }
    
    public CompteInfo getCompteById(Integer idCompte) {
        String url = baseUrl + "/api/compte-courant/" + id;
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                
                if (response.getCode() == 200) {
                    return objectMapper.readValue(jsonResponse, CompteInfo.class);
                } else {
                    logger.warn("Compte {} non trouvé - Status: {}", idCompte, response.getCode());
                    return null;
                }
            }
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel à CurrentAccount pour le compte {}", idCompte, e);
            return null;
        }
    }
    
    public List<CompteInfo> getAllComptes() {
        String url = baseUrl + "/api/compte-courant";
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                
                if (response.getCode() == 200) {
                    CompteInfo[] comptes = objectMapper.readValue(jsonResponse, CompteInfo[].class);
                    return Arrays.asList(comptes);
                } else {
                    logger.error("Erreur lors de la récupération des comptes - Status: {}", response.getCode());
                    return List.of();
                }
            }
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel à CurrentAccount pour tous les comptes", e);
            return List.of();
        }
    }
}