package com.example.banking.central.model;

import java.math.BigDecimal;
import java.util.List;

public class CompteAvecDetails {
    private CompteInfo compte;
    private List<PretInfo> prets;
    private List<DepotInfo> depots;
    private BigDecimal totalEngagements;
    private BigDecimal soldeNet;
    
    public CompteAvecDetails() {}
    
    public CompteAvecDetails(CompteInfo compte, List<PretInfo> prets, List<DepotInfo> depots) {
        this.compte = compte;
        this.prets = prets;
        this.depots = depots;
        calculerTotaux();
    }
    
    private void calculerTotaux() {
        // Calcul du total des engagements (prêts)
        this.totalEngagements = prets.stream()
            .map(PretInfo::getMontantPret)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calcul du solde net (solde compte + dépots - prêts)
        BigDecimal totalDepots = depots.stream()
            .map(DepotInfo::getMontantDepot)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.soldeNet = compte.getSoldeCompteCourant()
            .add(totalDepots)
            .subtract(totalEngagements);
    }
    
    // Getters and Setters
    public CompteInfo getCompte() { return compte; }
    public void setCompte(CompteInfo compte) { 
        this.compte = compte; 
        calculerTotaux();
    }
    
    public List<PretInfo> getPrets() { return prets; }
    public void setPrets(List<PretInfo> prets) { 
        this.prets = prets; 
        calculerTotaux();
    }
    
    public List<DepotInfo> getDepots() { return depots; }
    public void setDepots(List<DepotInfo> depots) { 
        this.depots = depots; 
        calculerTotaux();
    }
    
    public BigDecimal getTotalEngagements() { return totalEngagements; }
    public BigDecimal getSoldeNet() { return soldeNet; }
}