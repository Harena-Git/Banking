package com.example.banking.loan.dao;

import com.example.banking.loan.model.PretValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PretValuesDAO {
    private static final Logger logger = LoggerFactory.getLogger(PretValuesDAO.class);
    
    private static final String FIND_BY_PRET = "SELECT * FROM pret_values WHERE id_pret = ? ORDER BY date_creation DESC";
    private static final String FIND_BY_ID = "SELECT * FROM pret_values WHERE id_pret_values = ?";
    private static final String INSERT = "INSERT INTO pret_values (pourcentage_pret_values, duree_pret_values, id_pret, type_taux) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE pret_values SET pourcentage_pret_values = ?, duree_pret_values = ?, type_taux = ? WHERE id_pret_values = ?";
    private static final String DELETE = "DELETE FROM pret_values WHERE id_pret_values = ?";
    private static final String FIND_CURRENT_BY_PRET = "SELECT * FROM pret_values WHERE id_pret = ? ORDER BY date_creation DESC LIMIT 1";
    
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
            
            logger.info("Trouvé {} configurations de taux pour le prêt {}", pretValuesList.size(), idPret);
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des taux du prêt {}", idPret, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return pretValuesList;
    }
    
    public Optional<PretValues> findById(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToPretValues(rs));
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la configuration de taux {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return Optional.empty();
    }
    
    public PretValues findCurrentByPret(Integer idPret) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_CURRENT_BY_PRET)) {
            
            stmt.setInt(1, idPret);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPretValues(rs);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche du taux actuel du prêt {}", idPret, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return null;
    }
    
    public PretValues create(PretValues pretValues) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setBigDecimal(1, pretValues.getPourcentagePretValues());
            stmt.setInt(2, pretValues.getDureePretValues());
            stmt.setInt(3, pretValues.getIdPret());
            stmt.setString(4, pretValues.getTypeTaux());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la configuration de taux, aucune ligne affectée");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pretValues.setIdPretValues(generatedKeys.getInt(1));
                    logger.info("Configuration de taux créée avec ID: {}", pretValues.getIdPretValues());
                } else {
                    throw new SQLException("Échec de la création de la configuration de taux, aucun ID obtenu");
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la création de la configuration de taux", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return pretValues;
    }
    
    public PretValues update(PretValues pretValues) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            
            stmt.setBigDecimal(1, pretValues.getPourcentagePretValues());
            stmt.setInt(2, pretValues.getDureePretValues());
            stmt.setString(3, pretValues.getTypeTaux());
            stmt.setInt(4, pretValues.getIdPretValues());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new RuntimeException("Configuration de taux non trouvée: " + pretValues.getIdPretValues());
            }
            
            logger.info("Configuration de taux {} mise à jour", pretValues.getIdPretValues());
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de la configuration de taux {}", pretValues.getIdPretValues(), e);
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
                logger.info("Configuration de taux {} supprimée", id);
            }
            
            return deleted;
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression de la configuration de taux {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
    }
    
    private PretValues mapResultSetToPretValues(ResultSet rs) throws SQLException {
        PretValues pretValues = new PretValues();
        pretValues.setIdPretValues(rs.getInt("id_pret_values"));
        pretValues.setPourcentagePretValues(rs.getBigDecimal("pourcentage_pret_values"));
        pretValues.setDureePretValues(rs.getInt("duree_pret_values"));
        pretValues.setIdPret(rs.getInt("id_pret"));
        pretValues.setTypeTaux(rs.getString("type_taux"));
        pretValues.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());
        return pretValues;
    }
}