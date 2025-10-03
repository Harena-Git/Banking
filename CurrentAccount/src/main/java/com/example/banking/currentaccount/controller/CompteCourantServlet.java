package com.example.banking.currentaccount.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.currentaccount.model.CompteCourant;
import com.example.banking.currentaccount.model.Transaction;
import com.example.banking.currentaccount.service.CompteCourantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CompteCourantServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CompteCourantServlet.class);
    private final CompteCourantService compteService;
    private final ObjectMapper objectMapper;
    
    public CompteCourantServlet() {
        this.compteService = new CompteCourantService();
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/compte-courant - Liste tous les comptes
                List<CompteCourant> comptes = compteService.getAllComptes();
                sendJsonResponse(resp, HttpServletResponse.SC_OK, comptes);
                
            } else {
                String[] pathParts = pathInfo.split("/");
                
                if (pathParts.length == 2) {
                    // GET /api/compte-courant/{id} - Détails d'un compte
                    Integer id = Integer.parseInt(pathParts[1]);
                    Optional<CompteCourant> compte = compteService.getCompteById(id);
                    
                    if (compte.isPresent()) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, compte.get());
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Compte non trouvé");
                    }
                    
                } else if (pathParts.length == 3 && "transactions".equals(pathParts[2])) {
                    // GET /api/compte-courant/{id}/transactions - Transactions d'un compte
                    Integer id = Integer.parseInt(pathParts[1]);
                    List<Transaction> transactions = compteService.getTransactionsByCompte(id);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, transactions);
                    
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
                }
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de compte invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de compte invalide");
        } catch (Exception e) {
            logger.error("Erreur interne du serveur", e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // POST /api/compte-courant - Créer un compte
                CompteCourant compte = objectMapper.readValue(req.getReader(), CompteCourant.class);
                
                // Validation
                if (compte.getSoldeCompteCourant() == null) {
                    compte.setSoldeCompteCourant(BigDecimal.ZERO);
                }
                
                CompteCourant created = compteService.createCompte(compte);
                sendJsonResponse(resp, HttpServletResponse.SC_CREATED, created);
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (IllegalArgumentException e) {
            logger.error("Erreur de validation", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur interne du serveur", e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo != null && pathInfo.split("/").length == 2) {
                String[] pathParts = pathInfo.split("/");
                Integer id = Integer.parseInt(pathParts[1]);
                
                // PUT /api/compte-courant/{id} - Mettre à jour un compte
                CompteCourant compte = objectMapper.readValue(req.getReader(), CompteCourant.class);
                
                if (compte.getSoldeCompteCourant() != null) {
                    compteService.updateSolde(id, compte.getSoldeCompteCourant());
                    
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Solde mis à jour avec succès");
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Solde requis");
                }
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de compte invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de compte invalide");
        } catch (IllegalArgumentException e) {
            logger.error("Erreur de validation", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur interne du serveur", e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo != null && pathInfo.split("/").length == 2) {
                String[] pathParts = pathInfo.split("/");
                Integer id = Integer.parseInt(pathParts[1]);
                
                // DELETE /api/compte-courant/{id} - Supprimer un compte
                boolean deleted = compteService.deleteCompte(id);
                
                if (deleted) {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Compte supprimé avec succès");
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Compte non trouvé");
                }
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de compte invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de compte invalide");
        } catch (Exception e) {
            logger.error("Erreur interne du serveur", e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
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
        objectMapper.writeValue(resp.getWriter(), errorResponse);
    }
}