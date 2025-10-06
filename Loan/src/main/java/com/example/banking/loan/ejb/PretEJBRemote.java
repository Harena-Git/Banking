package com.example.banking.loan.ejb;

import com.example.banking.loan.model.Pret;
import com.example.banking.loan.model.PretValues;
import jakarta.ejb.Remote;
import java.util.List;

/**
 * Interface EJB Remote pour la gestion des prêts
 * Accessible à distance depuis d'autres microservices
 */
@Remote
public interface PretEJBRemote {

    /**
     * Récupère tous les prêts
     * @return Liste de tous les prêts
     */
    List<Pret> getAllPrets();

    /**
     * Récupère les prêts d'un compte spécifique
     * @param idCompte Identifiant du compte
     * @return Liste des prêts du compte
     */
    List<Pret> getPretsByCompte(Integer idCompte);

    /**
     * Crée un nouveau prêt
     * @param pret Le prêt à créer
     * @return Le prêt créé avec son ID
     */
    Pret createPret(Pret pret);

    /**
     * Recherche un prêt par son ID
     * @param id Identifiant du prêt
     * @return Le prêt trouvé ou null
     */
    Pret getPretById(Integer id);

    /**
     * Met à jour les informations d'un prêt
     * @param id Identifiant du prêt
     * @param pret Nouvelles données du prêt
     * @return Le prêt mis à jour
     */
    Pret updatePret(Integer id, Pret pret);

    /**
     * Supprime un prêt
     * @param id Identifiant du prêt
     * @return true si suppression réussie
     */
    boolean deletePret(Integer id);

    /**
     * Récupère les valeurs (taux, durée) d'un prêt
     * @param idPret Identifiant du prêt
     * @return Les valeurs du prêt
     */
    List<PretValues> getPretValuesByPret(Integer idPret);

    /**
     * Calcule les mensualités d'un prêt
     * @param montant Montant du prêt
     * @param taux Taux d'intérêt annuel
     * @param dureeMois Durée en mois
     * @return Montant de la mensualité
     */
    Double calculerMensualite(Double montant, Double taux, Integer dureeMois);
}