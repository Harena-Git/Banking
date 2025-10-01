package com.example.banking.loan.controller;

import com.example.banking.loan.model.PretValues;
import com.example.banking.loan.service.PretValuesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PretValuesServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PretValuesServlet.class);
    private final PretValuesService pretValuesService;
    private final ObjectMapper objectMapper;
    
    public PretValuesServlet() {
        this.pretValuesService = new PretValuesService();
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide. Spécifiez un prêt.");
                
            } else {
                String[] pathParts = pathInfo.split("/");
                
                if (pathParts.length == 2) {
                    // GET /api/pret-values/{id} - Détails d'une configuration de taux
                    Integer id = Integer.parseInt(pathParts[1]);
                    Optional<PretValues> pretValues = pretValuesService.getTauxById(id);
                    
                    if (pretValues.isPresent()) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, pretValues.get());
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Configuration de taux non trouvée");
                    }
                    
                } else if (pathParts.length == 3 && "pret".equals(pathParts[1])) {
                    // GET /api/pret-values/pret/{idPret} - Taux d'un prêt
                    Integer idPret = Integer.parseInt(pathParts[2]);
                    List<PretValues> tauxList = pretValuesService.getTauxByPret(idPret);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, tauxList);
                    
                } else if (pathParts.length == 4 && "pret".equals(pathParts[1]) && "actuel".equals(pathParts[3])) {
                    // GET /api/pret-values/pret/{idPret}/actuel - Taux actuel d'un prêt
                    Integer idPret = Integer.parseInt(pathParts[2]);
                    PretValues tauxActuel = pretValuesService.getTauxActuelByPret(idPret);
                    
                    if (tauxActuel != null) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, tauxActuel);
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Aucun taux défini pour ce prêt");
                    }
                    
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
                }
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID invalide");
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
                // POST /api/pret-values - Créer une configuration de taux
                PretValues pretValues = objectMapper.readValue(req.getReader(), PretValues.class);
                
                PretValues created = pretValuesService.createTaux(pretValues);
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
                
                // PUT /api/pret-values/{id} - Mettre à jour une configuration de taux
                PretValues pretValues = objectMapper.readValue(req.getReader(), PretValues.class);
                pretValues.setIdPretValues(id);
                
                PretValues updated = pretValuesService.updateTaux(pretValues);
                sendJsonResponse(resp, HttpServletResponse.SC_OK, updated);
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de configuration de taux invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de configuration de taux invalide");
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
                
                // DELETE /api/pret-values/{id} - Supprimer une configuration de taux
                boolean deleted = pretValuesService.deleteTaux(id);
                
                if (deleted) {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Configuration de taux supprimée avec succès");
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Configuration de taux non trouvée");
                }
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de configuration de taux invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de configuration de taux invalide");
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