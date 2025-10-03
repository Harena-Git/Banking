package com.example.banking.loan.service;

import com.example.banking.loan.dao.PretDAO;
import com.example.banking.loan.dao.PretValuesDAO;
import com.example.banking.loan.model.Pret;
import com.example.banking.loan.model.PretValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PretService {
    private static final Logger logger = LoggerFactory.getLogger(PretService.class);
    
    private final PretDAO pretDAO;
    private final PretValuesDAO pretValuesDAO;
    
    public PretService() {
        this.pretDAO = new PretDAO();
        this.pretValuesDAO = new PretValuesDAO();
    }
    
    public List<Pret> getAllPrets() {
        logger.debug("Récupération de tous les prêts");
        return pretDAO.findAll();
    }
    
    public Optional<Pret> getPretById(Integer id) {
        logger.debug("Recherche du prêt avec ID: {}", id);
        return pretDAO.findById(id);
    }
    
    public List<Pret> getPretsByCompte(Integer idCompte) {
        logger.debug("Récupération des prêts du compte: {}", idCompte);
        return pretDAO.findByCompteCourant(idCompte);
    }
    
    public Pret createPret(Pret pret) {
        logger.info("Création d'un nouveau prêt: {}", pret);
        
        // Validation
        if (pret.getMontantPret() == null || pret.getMontantPret().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du prêt doit être positif");
        }
        
        if (pret.getTypePret() == null || pret.getTypePret().trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de prêt est requis");
        }
        
        if (pret.getIdCompteCourant() == null) {
            throw new IllegalArgumentException("L'ID du compte est requis");
        }
        
        return pretDAO.create(pret);
    }
    
    public void updateStatutPret(Integer idPret, String nouveauStatut) {
        logger.info("Mise à jour du statut du prêt {}: {}", idPret, nouveauStatut);
        
        if (!isStatutValide(nouveauStatut)) {
            throw new IllegalArgumentException("Statut invalide: " + nouveauStatut);
        }
        
        pretDAO.updateStatut(idPret, nouveauStatut);
    }
    
    public boolean deletePret(Integer id) {
        logger.info("Suppression du prêt: {}", id);
        return pretDAO.delete(id);
    }
    
    public List<PretValues> getTauxByPret(Integer idPret) {
        logger.debug("Récupération des taux du prêt: {}", idPret);
        return pretValuesDAO.findByPret(idPret);
    }
    
    public PretValues getTauxActuelByPret(Integer idPret) {
        logger.debug("Récupération du taux actuel du prêt: {}", idPret);
        return pretValuesDAO.findCurrentByPret(idPret);
    }
    
    private boolean isStatutValide(String statut) {
        return List.of("EN_ATTENTE", "APPROUVE", "REJETE", "REMBOURSE").contains(statut);
    }
    
    public BigDecimal calculerMensualite(Integer idPret) {
        Pret pret = pretDAO.findById(idPret)
            .orElseThrow(() -> new IllegalArgumentException("Prêt non trouvé: " + idPret));
        
        PretValues tauxActuel = pretValuesDAO.findCurrentByPret(idPret);
        if (tauxActuel == null) {
            throw new IllegalArgumentException("Aucun taux défini pour ce prêt");
        }
        
        return calculerMensualite(pret.getMontantPret(), tauxActuel.getPourcentagePretValues(), tauxActuel.getDureePretValues());
    }
    
    public BigDecimal calculerMensualite(BigDecimal montant, BigDecimal tauxAnnuel, Integer dureeMois) {
        if (tauxAnnuel.compareTo(BigDecimal.ZERO) == 0) {
            // Prêt sans intérêt
            return montant.divide(BigDecimal.valueOf(dureeMois), 2, BigDecimal.ROUND_HALF_UP);
        }
        
        // Conversion du taux annuel en taux mensuel
        BigDecimal tauxMensuel = tauxAnnuel.divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                                          .divide(BigDecimal.valueOf(12), 10, BigDecimal.ROUND_HALF_UP);
        
        // Formule de calcul de mensualité : M = [C × t × (1 + t)^n] / [(1 + t)^n - 1]
        BigDecimal unPlusTaux = BigDecimal.ONE.add(tauxMensuel);
        BigDecimal puissance = unPlusTaux.pow(dureeMois);
        
        BigDecimal numerateur = montant.multiply(tauxMensuel).multiply(puissance);
        BigDecimal denominateur = puissance.subtract(BigDecimal.ONE);
        
        return numerateur.divide(denominateur, 2, BigDecimal.ROUND_HALF_UP);
    }
}