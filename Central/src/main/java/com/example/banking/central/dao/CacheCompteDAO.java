package com.example.banking.central.dao;

import com.example.banking.central.model.CompteAvecDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class CacheCompteDAO {
    private static final Logger logger = LoggerFactory.getLogger(CacheCompteDAO.class);

    private static final String FIND_BY_COMPTE = "SELECT * FROM cache_compte_details WHERE id_compte = ?";
    private static final String INSERT = "INSERT INTO cache_compte_details (id_compte, solde_actuel, total_prets, total_depots) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cache_compte_details SET solde_actuel = ?, total_prets = ?, total_depots = ?, derniere_maj = CURRENT_TIMESTAMP WHERE id_compte = ?";
    private static final String DELETE_OLD = "DELETE FROM cache_compte_details WHERE derniere_maj < DATE_SUB(NOW(), INTERVAL 1 HOUR)";

    public CompteAvecDetails findByCompteId(Integer idCompte) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_COMPTE)) {

            stmt.setInt(1, idCompte);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Vérifier si le cache est encore frais (moins de 5 minutes)
                    Timestamp derniereMaj = rs.getTimestamp("derniere_maj");
                    if (derniereMaj.toLocalDateTime().isAfter(LocalDateTime.now().minusMinutes(5))) {
                        logger.debug("Cache frais trouvé pour le compte: {}", idCompte);

                        // Reconstruire l'objet CompteAvecDetails à partir du cache
                        // Note: Ceci est une version simplifiée
                        return new CompteAvecDetails();
                    } else {
                        logger.debug("Cache expiré pour le compte: {}", idCompte);
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche en cache du compte {}", idCompte, e);
        }

        return null;
    }

    public void saveOrUpdate(CompteAvecDetails compteDetails) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Vérifier si existe déjà
            boolean exists = false;
            try (PreparedStatement checkStmt = conn.prepareStatement(FIND_BY_COMPTE)) {
                checkStmt.setInt(1, compteDetails.getCompte().getIdCompteCourant());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    exists = rs.next();
                }
            }

            if (exists) {
                // Mise à jour
                try (PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
                    stmt.setBigDecimal(1, compteDetails.getCompte().getSoldeCompteCourant());
                    stmt.setBigDecimal(2, compteDetails.getTotalEngagements());
                    stmt.setBigDecimal(3, compteDetails.getDepots().stream()
                            .map(d -> d.getMontantDepot())
                            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
                    stmt.setInt(4, compteDetails.getCompte().getIdCompteCourant());

                    stmt.executeUpdate();
                    logger.debug("Cache mis à jour pour le compte: {}", compteDetails.getCompte().getIdCompteCourant());
                }
            } else {
                // Insertion
                try (PreparedStatement stmt = conn.prepareStatement(INSERT)) {
                    stmt.setInt(1, compteDetails.getCompte().getIdCompteCourant());
                    stmt.setBigDecimal(2, compteDetails.getCompte().getSoldeCompteCourant());
                    stmt.setBigDecimal(3, compteDetails.getTotalEngagements());
                    stmt.setBigDecimal(4, compteDetails.getDepots().stream()
                            .map(d -> d.getMontantDepot())
                            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

                    stmt.executeUpdate();
                    logger.debug("Cache créé pour le compte: {}", compteDetails.getCompte().getIdCompteCourant());
                }
            }

            // Nettoyer les anciennes entrées
            try (PreparedStatement cleanStmt = conn.prepareStatement(DELETE_OLD)) {
                cleanStmt.executeUpdate();
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la sauvegarde en cache du compte {}",
                    compteDetails.getCompte().getIdCompteCourant(), e);
        }
    }

    public void cleanOldCache() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_OLD)) {

            int deleted = stmt.executeUpdate();
            logger.info("{} entrées de cache nettoyées", deleted);

        } catch (SQLException e) {
            logger.error("Erreur lors du nettoyage du cache", e);
        }
    }
}