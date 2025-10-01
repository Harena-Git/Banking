package com.example.banking.loan.service;

import com.example.banking.loan.dao.PretDAO;
import com.example.banking.loan.dao.PretValuesDAO;
import com.example.banking.loan.model.PretValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PretValuesService {
    private static final Logger logger = LoggerFactory.getLogger(PretValuesService.class);
    
    private final PretValuesDAO pretValuesDAO;
    private final PretDAO pretDAO;
    
    public PretValuesService() {
        this.pretValuesDAO = new PretValuesDAO();
        this.pretDAO = new PretDAO();
    }
    
    public List<PretValues> getTauxByPret(Integer idPret) {
        logger.debug("Récupération des taux du prêt: {}", idPret);
        return pretValuesDAO.findByPret(idPret);
    }
    
    public Optional<PretValues> getTauxById(Integer id) {
        logger.debug("Recherche de la configuration de taux avec ID: {}", id);
        return pretValuesDAO.findById(id);
    }
    
    public PretValues getTauxActuelByPret(Integer idPret) {
        logger.debug("Récupération du taux actuel du prêt: {}", idPret);
        return pretValuesDAO.findCurrentByPret(idPret);
    }
    
    public PretValues createTaux(PretValues pretValues) {
        logger.info("Création d'une nouvelle configuration de taux: {}", pretValues);
        
        // Validation
        if (pretValues.getPourcentagePretValues() == null || pretValues.getPourcentagePretValues().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le pourcentage du taux doit être positif");
        }
        
        if (pretValues.getDureePretValues() == null || pretValues.getDureePretValues() <= 0) {
            throw new IllegalArgumentException("La durée doit être positive");
        }
        
        if (pretValues.getIdPret() == null) {
            throw new IllegalArgumentException("L'ID du prêt est requis");
        }
        
        // Vérifier que le prêt existe
        if (!pretDAO.findById(pretValues.getIdPret()).isPresent()) {
            throw new IllegalArgumentException("Le prêt n'existe pas: " + pretValues.getIdPret());
        }
        
        return pretValuesDAO.create(pretValues);
    }
    
    public PretValues updateTaux(PretValues pretValues) {
        logger.info("Mise à jour de la configuration de taux: {}", pretValues);
        
        // Validation
        if (pretValues.getPourcentagePretValues() == null || pretValues.getPourcentagePretValues().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le pourcentage du taux doit être positif");
        }
        
        if (pretValues.getDureePretValues() == null || pretValues.getDureePretValues() <= 0) {
            throw new IllegalArgumentException("La durée doit être positive");
        }
        
        return pretValuesDAO.update(pretValues);
    }
    
    public boolean deleteTaux(Integer id) {
        logger.info("Suppression de la configuration de taux: {}", id);
        return pretValuesDAO.delete(id);
    }
    
    public boolean isTypeTauxValide(String typeTaux) {
        return List.of("FIXE", "VARIABLE").contains(typeTaux);
    }
}