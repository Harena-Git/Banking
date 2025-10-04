package com.example.banking.currentaccount.ejb;

import com.example.banking.currentaccount.dao.CompteCourantDAO;
import com.example.banking.currentaccount.model.CompteCourant;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * EJB Stateless pour la gestion des comptes courants
 * Peut être appelé à distance via l'interface CompteCourantEJBRemote
 */
@Stateless
public class CompteCourantEJB implements CompteCourantEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(CompteCourantEJB.class);

    private final CompteCourantDAO compteCourantDAO;

    // Injection par constructeur
    public CompteCourantEJB() {
        this.compteCourantDAO = new CompteCourantDAO();
        logger.info("EJB CompteCourantEJB initialisé");
    }

    @Override
    public List<CompteCourant> getAllComptes() {
        logger.info("EJB - Récupération de tous les comptes");
        return compteCourantDAO.findAll();
    }

    @Override
    public CompteCourant getCompteById(Integer id) {
        logger.info("EJB - Recherche du compte: {}", id);
        return compteCourantDAO.findById(id).orElse(null);
    }

    @Override
    public CompteCourant createCompte(CompteCourant compte) {
        logger.info("EJB - Création d'un nouveau compte");
        return compteCourantDAO.create(compte);
    }

    @Override
    public boolean compteExiste(Integer idCompte) {
        logger.debug("EJB - Vérification existence compte: {}", idCompte);
        return compteCourantDAO.findById(idCompte).isPresent();
    }

    @Override
    public void updateSolde(Integer idCompte, Double nouveauSolde) {
        logger.info("EJB - Mise à jour solde compte {}: {}", idCompte, nouveauSolde);
        // Conversion Double to BigDecimal et appel DAO
        compteCourantDAO.updateSolde(idCompte, java.math.BigDecimal.valueOf(nouveauSolde));
    }
}