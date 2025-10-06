package com.example.banking.loan.ejb;

import com.example.banking.loan.model.PretValues;
import jakarta.ejb.Remote;
import java.util.List;

/**
 * Interface EJB Remote pour la gestion des valeurs de prêt (taux, durée)
 */
@Remote
public interface PretValuesEJBRemote {

    /**
     * Récupère toutes les valeurs de prêt
     * @return Liste de toutes les valeurs
     */
    List<PretValues> getAllPretValues();

    /**
     * Crée de nouvelles valeurs de prêt
     * @param pretValues Les valeurs à créer
     * @return Les valeurs créées
     */
    PretValues createPretValues(PretValues pretValues);

    /**
     * Récupère les valeurs d'un prêt spécifique
     * @param idPret Identifiant du prêt
     * @return Liste des valeurs du prêt
     */
    List<PretValues> getValuesByPret(Integer idPret);
}