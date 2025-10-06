package com.example.banking.central.ejb;

import com.example.banking.central.model.CompteAvecDetails;
import com.example.banking.central.model.CompteInfo;
import com.example.banking.central.model.PretInfo;
import com.example.banking.central.model.DepotInfo;
import jakarta.ejb.Remote;
import java.util.List;

/**
 * Interface EJB Remote pour l'agrégation centrale
 * Permet à d'autres services d'accéder aux données agrégées
 */
@Remote
public interface CentralAggregationEJBRemote {

    /**
     * Récupère les détails complets d'un compte (solde + prêts + dépôts)
     * @param idCompte Identifiant du compte
     * @return Détails complets du compte
     */
    CompteAvecDetails getCompteAvecDetails(Integer idCompte);

    /**
     * Récupère la liste de tous les comptes
     * @return Liste de tous les comptes
     */
    List<CompteInfo> getAllComptes();

    /**
     * Récupère les informations de base d'un compte
     * @param idCompte Identifiant du compte
     * @return Informations du compte
     */
    CompteInfo getCompteInfo(Integer idCompte);

    /**
     * Récupère les prêts d'un compte
     * @param idCompte Identifiant du compte
     * @return Liste des prêts du compte
     */
    List<PretInfo> getPretsByCompte(Integer idCompte);

    /**
     * Récupère les dépôts d'un compte
     * @param idCompte Identifiant du compte
     * @return Liste des dépôts du compte
     */
    List<DepotInfo> getDepotsByCompte(Integer idCompte);

    /**
     * Vérifie la santé de tous les microservices
     * @return Statut de santé formaté
     */
    String healthCheck();

    /**
     * Calcule le score de crédit d'un compte
     * @param idCompte Identifiant du compte
     * @return Score de crédit (0-1000)
     */
    Integer calculerScoreCredit(Integer idCompte);

    /**
     * Récupère l'historique complet d'un compte
     * @param idCompte Identifiant du compte
     * @return Historique agrégé
     */
    List<Object> getHistoriqueComplet(Integer idCompte);
}