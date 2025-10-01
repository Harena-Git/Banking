package com.example.banking.central.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.central.model.CompteCourant;

public class CompteCourantDAO {
    private static final Logger logger = LoggerFactory.getLogger(CompteCourantDAO.class);
    
    private static final String FIND_ALL = "SELECT * FROM compte_courant ORDER BY id_compte_courant";
    private static final String FIND_BY_ID = "SELECT * FROM compte_courant WHERE id_compte_courant = ?";
    private static final String INSERT = "INSERT INTO compte_courant (solde_compte_courant, statut) VALUES (?, ?)";
    private static final String UPDATE_SOLDE = "UPDATE compte_courant SET solde_compte_courant = ? WHERE id_compte_courant = ?";
    private static final String DELETE = "UPDATE compte_courant SET statut = 'INACTIF' WHERE id_compte_courant = ?";
    
    public List<CompteCourant> findAll() {
        List<CompteCourant> comptes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                comptes.add(mapResultSetToCompte(rs));
            }
            
            logger.info("Trouvé {} comptes", comptes.size());
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des comptes", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return comptes;
    }
    
    public Optional<CompteCourant> findById(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCompte(rs));
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche du compte {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return Optional.empty();
    }
    
    public CompteCourant create(CompteCourant compte) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setBigDecimal(1, compte.getSoldeCompteCourant());
            stmt.setString(2, compte.getStatut() != null ? compte.getStatut() : "ACTIF");
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création du compte, aucune ligne affectée");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    compte.setIdCompteCourant(generatedKeys.getInt(1));
                    logger.info("Compte créé avec ID: {}", compte.getIdCompteCourant());
                } else {
                    throw new SQLException("Échec de la création du compte, aucun ID obtenu");
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la création du compte", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return compte;
    }
    
    public void updateSolde(Integer idCompte, BigDecimal nouveauSolde) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SOLDE)) {
            
            stmt.setBigDecimal(1, nouveauSolde);
            stmt.setInt(2, idCompte);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new RuntimeException("Compte non trouvé: " + idCompte);
            }
            
            logger.info("Solde mis à jour pour le compte {}: {}", idCompte, nouveauSolde);
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour du solde du compte {}", idCompte, e);
            throw new RuntimeException("Erreur base de données", e);
        }
    }
    
    public boolean delete(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            boolean deleted = affectedRows > 0;
            if (deleted) {
                logger.info("Compte {} marqué comme INACTIF", id);
            }
            
            return deleted;
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression du compte {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
    }
    
    private CompteCourant mapResultSetToCompte(ResultSet rs) throws SQLException {
        return new CompteCourant(
            rs.getInt("id_compte_courant"),
            rs.getBigDecimal("solde_compte_courant"),
            rs.getTimestamp("date_creation").toLocalDateTime(),
            rs.getString("statut")
        );
    }
}