package com.example.banking.currentaccount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.banking.currentaccount.model.Transaction;

public class TransactionDAO {
    private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
    
    private static final String FIND_BY_COMPTE = "SELECT * FROM transaction WHERE id_compte_courant = ? ORDER BY date_transaction DESC";
    private static final String FIND_ALL = "SELECT * FROM transaction ORDER BY date_transaction DESC";
    private static final String INSERT = "INSERT INTO transaction (montant_transaction, type_transaction, id_compte_courant, description, compte_destinataire) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM transaction WHERE id_transaction = ?";
    
    public List<Transaction> findByCompteCourant(Integer idCompte) {
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_COMPTE)) {
            
            stmt.setInt(1, idCompte);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
            
            logger.info("Trouvé {} transactions pour le compte {}", transactions.size(), idCompte);
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des transactions du compte {}", idCompte, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return transactions;
    }
    
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
            
            logger.info("Trouvé {} transactions", transactions.size());
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération de toutes les transactions", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return transactions;
    }
    
    public Transaction create(Transaction transaction) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setBigDecimal(1, transaction.getMontantTransaction());
            stmt.setString(2, transaction.getTypeTransaction());
            stmt.setInt(3, transaction.getIdCompteCourant());
            stmt.setString(4, transaction.getDescription());
            
            if (transaction.getCompteDestinataire() != null) {
                stmt.setInt(5, transaction.getCompteDestinataire());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la transaction, aucune ligne affectée");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setIdTransaction(generatedKeys.getInt(1));
                    logger.info("Transaction créée avec ID: {}", transaction.getIdTransaction());
                } else {
                    throw new SQLException("Échec de la création de la transaction, aucun ID obtenu");
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la création de la transaction", e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return transaction;
    }
    
    public Transaction findById(Integer id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la transaction {}", id, e);
            throw new RuntimeException("Erreur base de données", e);
        }
        
        return null;
    }
    
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setIdTransaction(rs.getInt("id_transaction"));
        transaction.setDateTransaction(rs.getTimestamp("date_transaction").toLocalDateTime());
        transaction.setMontantTransaction(rs.getBigDecimal("montant_transaction"));
        transaction.setTypeTransaction(rs.getString("type_transaction"));
        transaction.setIdCompteCourant(rs.getInt("id_compte_courant"));
        transaction.setDescription(rs.getString("description"));
        
        int compteDest = rs.getInt("compte_destinataire");
        if (!rs.wasNull()) {
            transaction.setCompteDestinataire(compteDest);
        }
        
        return transaction;
    }
}