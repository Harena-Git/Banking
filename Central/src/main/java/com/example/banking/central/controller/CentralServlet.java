package com.example.banking.central.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.central.model.CompteAvecDetails;
import com.example.banking.central.model.CompteInfo;
import com.example.banking.central.model.DepotInfo;
import com.example.banking.central.model.PretInfo;
import com.example.banking.central.service.CentralService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CentralServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CentralServlet.class);
    private CentralService centralService;
    private ObjectMapper objectMapper;  // CORRECTION: Retirer final
    
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Charger la configuration
            Properties config = new Properties();
            try (var input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (input != null) {
                    config.load(input);
                } else {
                    // Configuration par défaut
                    config.setProperty("currentaccount.url", "http://localhost:8081/currentaccount");
                    config.setProperty("loan.url", "http://localhost:8082/loan");
                    config.setProperty("deposit.url", "http://localhost:8084/depositaccount");
                }
            }
            
            this.centralService = new CentralService(config);
            this.objectMapper = new ObjectMapper();  // CORRECTION: Initialisation dans init()
            
            logger.info("CentralServlet initialisé avec succès");
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'initialisation de CentralServlet", e);
            throw new ServletException("Erreur d'initialisation", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/central - Page d'accueil/statut
                Map<String, String> status = new HashMap<>();
                status.put("service", "Central Microservice");
                status.put("version", "1.0.0");
                status.put("status", "OK");
                sendJsonResponse(resp, HttpServletResponse.SC_OK, status);
                
            } else {
                String[] pathParts = pathInfo.split("/");
                
                if (pathParts.length == 2 && "health".equals(pathParts[1])) {
                    // GET /api/central/health - Santé des services
                    String healthStatus = centralService.healthCheck();
                    Map<String, String> response = new HashMap<>();
                    response.put("health", healthStatus);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                    
                } else if (pathParts.length == 3 && "compte".equals(pathParts[1])) {
                    // GET /api/central/compte/{id} - Détails complets d'un compte
                    Integer idCompte = Integer.parseInt(pathParts[2]);
                    CompteAvecDetails compteDetails = centralService.getCompteAvecDetails(idCompte);
                    
                    if (compteDetails != null) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, compteDetails);
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Compte non trouvé");
                    }
                    
                } else if (pathParts.length == 2 && "comptes".equals(pathParts[1])) {
                    // GET /api/central/comptes - Liste de tous les comptes
                    List<CompteInfo> comptes = centralService.getAllComptes();
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, comptes);
                    
                } else if (pathParts.length == 4 && "compte".equals(pathParts[1]) && "prets".equals(pathParts[3])) {
                    // GET /api/central/compte/{id}/prets - Prêts d'un compte
                    Integer idCompte = Integer.parseInt(pathParts[2]);
                    List<PretInfo> prets = centralService.getPretsByCompte(idCompte);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, prets);
                    
                } else if (pathParts.length == 4 && "compte".equals(pathParts[1]) && "depots".equals(pathParts[3])) {
                    // GET /api/central/compte/{id}/depots - Dépôts d'un compte
                    Integer idCompte = Integer.parseInt(pathParts[2]);
                    List<DepotInfo> depots = centralService.getDepotsByCompte(idCompte);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, depots);
                    
                } else if (pathParts.length == 3 && "compte".equals(pathParts[1]) && "info".equals(pathParts[2])) {
                    // GET /api/central/compte/info/{id} - Info de base d'un compte
                    // Note: Cette route nécessite un paramètre query ?id=
                    String idParam = req.getParameter("id");
                    if (idParam != null) {
                        Integer idCompte = Integer.parseInt(idParam);
                        CompteInfo compte = centralService.getCompteInfo(idCompte);
                        if (compte != null) {
                            sendJsonResponse(resp, HttpServletResponse.SC_OK, compte);
                        } else {
                            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Compte non trouvé");
                        }
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Paramètre 'id' requis");
                    }
                    
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
                }
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de compte invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de compte invalide");
        } catch (Exception e) {
            logger.error("Erreur interne du serveur", e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur: " + e.getMessage());
        }
    }
    
    private void sendJsonResponse(HttpServletResponse resp, int status, Object data) throws IOException {
        resp.setStatus(status);
        objectMapper.writeValue(resp.getWriter(), data);
    }
    
    private void sendErrorResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        errorResponse.put("status", String.valueOf(status));
        objectMapper.writeValue(resp.getWriter(), errorResponse);
    }
    
    @Override
    public void destroy() {
        logger.info("CentralServlet détruit");
        super.destroy();
    }
}