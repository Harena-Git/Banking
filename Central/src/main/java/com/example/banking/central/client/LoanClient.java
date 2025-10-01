package com.example.banking.central.client;

import com.example.banking.central.model.PretInfo;
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

public class LoanClient {
    private static final Logger logger = LoggerFactory.getLogger(LoanClient.class);
    private final String baseUrl;
    private final ObjectMapper objectMapper;
    
    public LoanClient(Properties config) {
        this.baseUrl = config.getProperty("loan.url", "http://localhost:8082/loan");
        this.objectMapper = new ObjectMapper();
    }
    
    public List<PretInfo> getPretsByCompte(Integer idCompte) {
        String url = baseUrl + "/api/pret/compte/" + idCompte;
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                
                if (response.getCode() == 200) {
                    PretInfo[] prets = objectMapper.readValue(jsonResponse, PretInfo[].class);
                    return Arrays.asList(prets);
                } else {
                    logger.warn("Aucun prêt trouvé pour le compte {} - Status: {}", idCompte, response.getCode());
                    return List.of();
                }
            }
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel à Loan pour les prêts du compte {}", idCompte, e);
            return List.of();
        }
    }
}