package com.example.banking.loan.controller;

import com.example.banking.loan.model.Pret;
import com.example.banking.loan.model.PretValues;
import com.example.banking.loan.service.PretService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PretServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PretServlet.class);
    private final PretService pretService;
    private final ObjectMapper objectMapper;
    
    public PretServlet() {
        this.pretService = new PretService();
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/pret - Liste tous les prêts
                List<Pret> prets = pretService.getAllPrets();
                sendJsonResponse(resp, HttpServletResponse.SC_OK, prets);
                
            } else {
                String[] pathParts = pathInfo.split("/");
                
                if (pathParts.length == 2) {
                    // GET /api/pret/{id} - Détails d'un prêt
                    Integer id = Integer.parseInt(pathParts[1]);
                    Optional<Pret> pret = pretService.getPretById(id);
                    
                    if (pret.isPresent()) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, pret.get());
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Prêt non trouvé");
                    }
                    
                } else if (pathParts.length == 3 && "compte".equals(pathParts[1])) {
                    // GET /api/pret/compte/{idCompte} - Prêts d'un compte
                    Integer idCompte = Integer.parseInt(pathParts[2]);
                    List<Pret> prets = pretService.getPretsByCompte(idCompte);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, prets);
                    
                } else if (pathParts.length == 4 && "taux".equals(pathParts[2])) {
                    // GET /api/pret/{id}/taux - Taux d'un prêt
                    Integer idPret = Integer.parseInt(pathParts[1]);
                    List<PretValues> taux = pretService.getTauxByPret(idPret);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, taux);
                    
                } else if (pathParts.length == 4 && "taux-actuel".equals(pathParts[2])) {
                    // GET /api/pret/{id}/taux-actuel - Taux actuel d'un prêt
                    Integer idPret = Integer.parseInt(pathParts[1]);
                    PretValues tauxActuel = pretService.getTauxActuelByPret(idPret);
                    
                    if (tauxActuel != null) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, tauxActuel);
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Aucun taux défini pour ce prêt");
                    }
                    
                } else if (pathParts.length == 4 && "mensualite".equals(pathParts[2])) {
                    // GET /api/pret/{id}/mensualite - Calcul de mensualité
                    Integer idPret = Integer.parseInt(pathParts[1]);
                    BigDecimal mensualite = pretService.calculerMensualite(idPret);
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("mensualite", mensualite);
                    response.put("devise", "EUR");
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                    
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
                }
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID invalide");
        } catch (IllegalArgumentException e) {
            logger.error("Erreur de validation", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
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
                // POST /api/pret - Créer un prêt
                Pret pret = objectMapper.readValue(req.getReader(), Pret.class);
                
                Pret created = pretService.createPret(pret);
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
                
                // PUT /api/pret/{id} - Mettre à jour le statut d'un prêt
                Map<String, String> requestBody = objectMapper.readValue(req.getReader(), Map.class);
                String nouveauStatut = requestBody.get("statut");
                
                if (nouveauStatut != null && !nouveauStatut.trim().isEmpty()) {
                    pretService.updateStatutPret(id, nouveauStatut);
                    
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Statut du prêt mis à jour avec succès");
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Le statut est requis");
                }
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de prêt invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de prêt invalide");
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
                
                // DELETE /api/pret/{id} - Supprimer un prêt
                boolean deleted = pretService.deletePret(id);
                
                if (deleted) {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Prêt supprimé avec succès");
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
                } else {
                    sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Prêt non trouvé");
                }
                
            } else {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "URL non valide");
            }
            
        } catch (NumberFormatException e) {
            logger.error("ID de prêt invalide", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de prêt invalide");
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