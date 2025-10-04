package com.example.banking.currentaccount.ejb;

import com.example.banking.currentaccount.model.CompteCourant;
import jakarta.ejb.Remote;
import java.util.List;

/**
 * Interface EJB Remote pour l'accès distant depuis d'autres microservices
 * Cette interface sera appelée par le Central via JNDI Lookup
 */
@Remote
public interface CompteCourantEJBRemote {

    /**
     * Récupère tous les comptes courants
     * @return Liste de tous les comptes
     */
    List<CompteCourant> getAllComptes();

    /**
     * Recherche un compte par son ID
     * @param id Identifiant du compte
     * @return Le compte trouvé ou null
     */
    CompteCourant getCompteById(Integer id);

    /**
     * Crée un nouveau compte courant
     * @param compte Le compte à créer
     * @return Le compte créé avec son ID généré
     */
    CompteCourant createCompte(CompteCourant compte);

    /**
     * Vérifie si un compte existe
     * @param idCompte Identifiant du compte
     * @return true si le compte existe
     */
    boolean compteExiste(Integer idCompte);

    /**
     * Met à jour le solde d'un compte
     * @param idCompte Identifiant du compte
     * @param nouveauSolde Nouveau solde
     */
    void updateSolde(Integer idCompte, Double nouveauSolde);
}