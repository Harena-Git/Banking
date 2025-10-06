package com.example.banking.loan.dao;

import com.example.banking.loan.model.Pret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PretDAO {
    private static final Logger logger = LoggerFactory.getLogger(PretDAO.class);

    private static final String FIND_ALL = "SELECT * FROM pret ORDER BY id_pret";
    private static final String FIND_BY_ID = "SELECT * FROM pret WHERE id_pret = ?";
    private static final String FIND_BY_COMPTE = "SELECT * FROM pret WHERE id_compte_courant = ? ORDER BY id_pret";
    private static final String INSERT = "INSERT INTO pret (montant_pret, type_pret, id_compte_courant) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE pret SET montant_pret = ?, type_pret = ?, id_compte_courant = ? WHERE id_pret = ?";
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

    public Pret findById(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPret(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche du prêt {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return null;
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

            if (pret.getIdCompteCourant() != null) {
                stmt.setInt(3, pret.getIdCompteCourant());
            } else {
                stmt.setNull(3, Types.INTEGER);
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

    public Pret update(Pret pret) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setBigDecimal(1, pret.getMontantPret());
            stmt.setString(2, pret.getTypePret());

            if (pret.getIdCompteCourant() != null) {
                stmt.setInt(3, pret.getIdCompteCourant());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, pret.getIdPret());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Prêt non trouvé: " + pret.getIdPret());
            }

            logger.info("Prêt mis à jour: {}", pret.getIdPret());

        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour du prêt {}", pret.getIdPret(), e);
            throw new RuntimeException("Erreur base de données", e);
        }

        return pret;
    }

    public boolean delete(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            boolean deleted = affectedRows > 0;
            if (deleted) {
                logger.info("Prêt supprimé: {}", id);
            } else {
                logger.warn("Prêt non trouvé pour suppression: {}", id);
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

        int idCompte = rs.getInt("id_compte_courant");
        if (!rs.wasNull()) {
            pret.setIdCompteCourant(idCompte);
        }

        return pret;
    }
}