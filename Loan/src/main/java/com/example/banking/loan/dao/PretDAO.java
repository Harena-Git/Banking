package com.example.banking.loan.dao;

import com.example.banking.loan.model.Pret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PretDAO {
    private static final Logger logger = LoggerFactory.getLogger(PretDAO.class);
    
    private static final String FIND_ALL = "SELECT * FROM pret ORDER BY date_creation DESC";
    private static final String FIND_BY_ID = "SELECT * FROM pret WHERE id_pret = ?";
    private static final String FIND_BY_COMPTE = "SELECT * FROM pret WHERE id_compte_courant = ? ORDER BY date_creation DESC";
    private static final String INSERT = "INSERT INTO pret (montant_pret, type_pret, id_compte_courant, statut, date_debut, date_fin) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STATUT = "UPDATE pret SET statut = ? WHERE id_pret = ?";
    private static final String DELETE = "DELETE FROM pret WHERE id_pret = ?";
    
    public List<Pret> findAll() {
        List<Pret> prets = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                prets.add(mapResultSetToPret(rs));
            }
            
            logger.info("Trouvé {} prêts", prets.size());
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des prêts", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return prets;
    }
    
    public Optional<Pret> findById(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToPret(rs));
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche du prêt {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return Optional.empty();
    }
    
    public List<Pret> findByCompteCourant(Integer idCompte) {
        List<Pret> prets = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_COMPTE)) {
            
            stmt.setInt(1, idCompte);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    prets.add(mapResultSetToPret(rs));
                }
            }
            
            logger.info("Trouvé {} prêts pour le compte {}", prets.size(), idCompte);
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des prêts du compte {}", idCompte, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return prets;
    }
    
    public Pret create(Pret pret) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setBigDecimal(1, pret.getMontantPret());
            stmt.setString(2, pret.getTypePret());
            stmt.setInt(3, pret.getIdCompteCourant());
            stmt.setString(4, pret.getStatut());
            
            if (pret.getDateDebut() != null) {
                stmt.setDate(5, Date.valueOf(pret.getDateDebut()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            
            if (pret.getDateFin() != null) {
                stmt.setDate(6, Date.valueOf(pret.getDateFin()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création du prêt, aucune ligne affectée");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pret.setIdPret(generatedKeys.getInt(1));
                    logger.info("Prêt créé avec ID: {}", pret.getIdPret());
                } else {
                    throw new SQLException("Échec de la création du prêt, aucun ID obtenu");
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la création du prêt", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return pret;
    }
    
    public void updateStatut(Integer idPret, String nouveauStatut) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_STATUT)) {
            
            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idPret);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new RuntimeException("Prêt non trouvé: " + idPret);
            }
            
            logger.info("Statut du prêt {} mis à jour: {}", idPret, nouveauStatut);
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour du statut du prêt {}", idPret, e);
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
                logger.info("Prêt {} supprimé", id);
            }
            
            return deleted;
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression du prêt {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
    }
    
    private Pret mapResultSetToPret(ResultSet rs) throws SQLException {
        Pret pret = new Pret();
        pret.setIdPret(rs.getInt("id_pret"));
        pret.setMontantPret(rs.getBigDecimal("montant_pret"));
        pret.setTypePret(rs.getString("type_pret"));
        pret.setIdCompteCourant(rs.getInt("id_compte_courant"));
        pret.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());
        pret.setStatut(rs.getString("statut"));
        
        Date dateDebut = rs.getDate("date_debut");
        if (dateDebut != null) {
            pret.setDateDebut(dateDebut.toLocalDate());
        }
        
        Date dateFin = rs.getDate("date_fin");
        if (dateFin != null) {
            pret.setDateFin(dateFin.toLocalDate());
        }
        
        return pret;
    }
}