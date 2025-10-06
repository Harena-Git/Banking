package com.example.banking.loan.ejb;

import com.example.banking.loan.dao.PretDAO;
import com.example.banking.loan.dao.PretValuesDAO;
import com.example.banking.loan.model.Pret;
import com.example.banking.loan.model.PretValues;
import jakarta.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * EJB Stateless pour la gestion des prêts
 */
@Stateless
public class PretEJB implements PretEJBRemote {
    private static final Logger logger = LoggerFactory.getLogger(PretEJB.class);

    private final PretDAO pretDAO;
    private final PretValuesDAO pretValuesDAO;

    public PretEJB() {
        this.pretDAO = new PretDAO();
        this.pretValuesDAO = new PretValuesDAO();
        logger.info("EJB PretEJB initialisé");
    }

    @Override
    public List<Pret> getAllPrets() {
        logger.info("EJB Loan - Récupération de tous les prêts");
        return pretDAO.findAll();
    }

    @Override
    public List<Pret> getPretsByCompte(Integer idCompte) {
        logger.info("EJB Loan - Récupération des prêts pour le compte: {}", idCompte);
        return pretDAO.findByCompteCourant(idCompte);
    }

    @Override
    public Pret createPret(Pret pret) {
        logger.info("EJB Loan - Création d'un nouveau prêt");
        return pretDAO.create(pret);
    }

    @Override
    public Pret getPretById(Integer id) {
        logger.debug("EJB Loan - Recherche du prêt: {}", id);
        return pretDAO.findById(id);
    }

    @Override
    public Pret updatePret(Integer id, Pret pret) {
        logger.info("EJB Loan - Mise à jour du prêt: {}", id);
        // Implémentation de la mise à jour
        Pret existing = pretDAO.findById(id);
        if (existing != null) {
            existing.setMontantPret(pret.getMontantPret());
            existing.setTypePret(pret.getTypePret());
            // Mettre à jour en base
            // pretDAO.update(existing); // À implémenter dans DAO
            return existing;
        }
        return null;
    }

    @Override
    public boolean deletePret(Integer id) {
        logger.info("EJB Loan - Suppression du prêt: {}", id);
        // Implémentation de la suppression
        // return pretDAO.delete(id); // À implémenter dans DAO
        return false;
    }

    @Override
    public List<PretValues> getPretValuesByPret(Integer idPret) {
        logger.debug("EJB Loan - Récupération des valeurs du prêt: {}", idPret);
        return pretValuesDAO.findByPret(idPret);
    }

    @Override
    public Double calculerMensualite(Double montant, Double taux, Integer dureeMois) {
        logger.info("EJB Loan - Calcul mensualité: montant={}, taux={}, durée={} mois", montant, taux, dureeMois);

        if (montant <= 0 || taux <= 0 || dureeMois <= 0) {
            throw new IllegalArgumentException("Paramètres invalides pour le calcul de mensualité");
        }

        // Calcul de la mensualité : M = [C × t × (1 + t)^n] / [(1 + t)^n - 1]
        double tauxMensuel = taux / 100 / 12;
        double puissance = Math.pow(1 + tauxMensuel, dureeMois);
        double mensualite = (montant * tauxMensuel * puissance) / (puissance - 1);

        logger.debug("Mensualité calculée: {}", mensualite);
        return mensualite;
    }
}