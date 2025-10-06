package com.example.banking.loan.dao;

import com.example.banking.loan.model.PretValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PretValuesDAO {
    private static final Logger logger = LoggerFactory.getLogger(PretValuesDAO.class);

    private static final String FIND_ALL = "SELECT * FROM pret_values ORDER BY id_pret_values";
    private static final String FIND_BY_ID = "SELECT * FROM pret_values WHERE id_pret_values = ?";
    private static final String FIND_BY_PRET = "SELECT * FROM pret_values WHERE id_pret = ? ORDER BY id_pret_values";
    private static final String INSERT = "INSERT INTO pret_values (pourcentage_pret_values, duree_pret_values, id_pret) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE pret_values SET pourcentage_pret_values = ?, duree_pret_values = ?, id_pret = ? WHERE id_pret_values = ?";
    private static final String DELETE = "DELETE FROM pret_values WHERE id_pret_values = ?";

    public List<PretValues> findAll() {
        List<PretValues> pretValuesList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pretValuesList.add(mapResultSetToPretValues(rs));
            }

            logger.info("Trouvé {} valeurs de prêt", pretValuesList.size());

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des valeurs de prêt", e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return pretValuesList;
    }

    public PretValues findById(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPretValues(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la valeur de prêt {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return null;
    }

    public List<PretValues> findByPret(Integer idPret) {
        List<PretValues> pretValuesList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_PRET)) {

            stmt.setInt(1, idPret);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pretValuesList.add(mapResultSetToPretValues(rs));
                }
            }

            logger.info("Trouvé {} valeurs pour le prêt {}", pretValuesList.size(), idPret);

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des valeurs du prêt {}", idPret, e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return pretValuesList;
    }

    public PretValues create(PretValues pretValues) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setBigDecimal(1, pretValues.getPourcentagePretValues());
            stmt.setInt(2, pretValues.getDureePretValues());
            stmt.setInt(3, pretValues.getPret().getIdPret());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Échec de la création des valeurs de prêt, aucune ligne affectée");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pretValues.setIdPretValues(generatedKeys.getInt(1));
                    logger.info("Valeurs de prêt créées avec ID: {}", pretValues.getIdPretValues());
                } else {
                    throw new SQLException("Échec de la création des valeurs de prêt, aucun ID obtenu");
                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la création des valeurs de prêt", e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return pretValues;
    }

    public PretValues update(PretValues pretValues) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setBigDecimal(1, pretValues.getPourcentagePretValues());
            stmt.setInt(2, pretValues.getDureePretValues());
            stmt.setInt(3, pretValues.getPret().getIdPret());
            stmt.setInt(4, pretValues.getIdPretValues());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Valeurs de prêt non trouvées: " + pretValues.getIdPretValues());
            }

            logger.info("Valeurs de prêt mises à jour: {}", pretValues.getIdPretValues());

        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour des valeurs de prêt {}", pretValues.getIdPretValues(), e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return pretValues;
    }

    public boolean delete(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            boolean deleted = affectedRows > 0;
            if (deleted) {
                logger.info("Valeurs de prêt supprimées: {}", id);
            } else {
                logger.warn("Valeurs de prêt non trouvées pour suppression: {}", id);
            }

            return deleted;

        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression des valeurs de prêt {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
    }

    private PretValues mapResultSetToPretValues(ResultSet rs) throws SQLException {
        PretValues pretValues = new PretValues();
        pretValues.setIdPretValues(rs.getInt("id_pret_values"));
        pretValues.setPourcentagePretValues(rs.getBigDecimal("pourcentage_pret_values"));
        pretValues.setDureePretValues(rs.getInt("duree_pret_values"));

        // Créer un objet Pret minimal avec l'ID seulement
        // Note: Pour une relation complète, il faudrait faire une jointure
        Pret pret = new Pret();
        pret.setIdPret(rs.getInt("id_pret"));
        pretValues.setPret(pret);

        return pretValues;
    }
}