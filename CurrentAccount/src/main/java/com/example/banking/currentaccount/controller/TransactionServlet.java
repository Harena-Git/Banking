package com.example.banking.currentaccount.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.currentaccount.model.Transaction;
import com.example.banking.currentaccount.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TransactionServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServlet.class);
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;
    
    public TransactionServlet() {
        this.transactionService = new TransactionService();
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/transaction - Liste toutes les transactions
                List<Transaction> transactions = transactionService.getAllTransactions();
                sendJsonResponse(resp, HttpServletResponse.SC_OK, transactions);
                
            } else {
                String[] pathParts = pathInfo.split("/");
                
                if (pathParts.length == 2) {
                    // GET /api/transaction/{id} - Détails d'une transaction
                    Integer id = Integer.parseInt(pathParts[1]);
                    Transaction transaction = transactionService.getTransactionById(id);
                    
                    if (transaction != null) {
                        sendJsonResponse(resp, HttpServletResponse.SC_OK, transaction);
                    } else {
                        sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Transaction non trouvée");
                    }
                    
                } else if (pathParts.length == 3 && "compte".equals(pathParts[1])) {
                    // GET /api/transaction/compte/{idCompte} - Transactions d'un compte
                    Integer idCompte = Integer.parseInt(pathParts[2]);
                    List<Transaction> transactions = transactionService.getTransactionsByCompte(idCompte);
                    sendJsonResponse(resp, HttpServletResponse.SC_OK, transactions);
                    
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
                // POST /api/transaction - Créer une transaction
                Transaction transaction = objectMapper.readValue(req.getReader(), Transaction.class);
                
                Transaction created = transactionService.createTransaction(transaction);
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
    
    private void sendJsonResponse(HttpServletResponse resp, int status, Object data) throws IOException {
        resp.setStatus(status);
        objectMapper.writeValue(resp.getWriter(), data);
    }
    
    private void sendErrorResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        objectMapper.writeValue(resp.getWriter(), Map.of("error", message));
    }
}