package com.example.banking.currentaccount.ejb;

import com.example.banking.currentaccount.dao.CompteCourantDAO;
import com.example.banking.currentaccount.model.CompteCourant;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Stateless
public class CompteCourantEJB implements CompteCourantEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(CompteCourantEJB.class);
    
    private final CompteCourantDAO compteCourantDAO;
    
    public CompteCourantEJB() {
        this.compteCourantDAO = new CompteCourantDAO();
    }
    
    @Override
    public List<CompteCourant> getAllComptes() {
        logger.info("EJB - Récupération de tous les comptes");
        return compteCourantDAO.findAll();
    }
    
    @Override
    public CompteCourant getCompteById(Integer id) {
        logger.info("EJB - Recherche du compte: {}", id);
        Optional<CompteCourant> compte = compteCourantDAO.findById(id);
        return compte.orElse(null);
    }
    
    @Override
    public CompteCourant createCompte(CompteCourant compte) {
        logger.info("EJB - Création d'un nouveau compte");
        return compteCourantDAO.create(compte);
    }
    
    @Override
    public void updateSolde(Integer idCompte, Double nouveauSolde) {
        logger.info("EJB - Mise à jour du solde du compte {}: {}", idCompte, nouveauSolde);
        // Implémentation à adapter pour Double
    }
    
    @Override
    public boolean compteExiste(Integer idCompte) {
        return compteCourantDAO.findById(idCompte).isPresent();
    }
}