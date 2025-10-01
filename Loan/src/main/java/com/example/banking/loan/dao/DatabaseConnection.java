package com.example.banking.loan.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static BasicDataSource dataSource;
    
    static {
        try {
            Properties props = new Properties();
            try (InputStream input = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("database.properties")) {
                if (input == null) {
                    throw new RuntimeException("Fichier database.properties non trouvé");
                }
                props.load(input);
            }
            
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(props.getProperty("db.driver"));
            dataSource.setUrl(props.getProperty("db.url"));
            dataSource.setUsername(props.getProperty("db.username"));
            dataSource.setPassword(props.getProperty("db.password"));
            
            // Configuration du pool de connexions
            dataSource.setInitialSize(Integer.parseInt(props.getProperty("db.pool.initialSize", "5")));
            dataSource.setMaxTotal(Integer.parseInt(props.getProperty("db.pool.maxTotal", "20")));
            dataSource.setMaxIdle(Integer.parseInt(props.getProperty("db.pool.maxIdle", "10")));
            dataSource.setMinIdle(Integer.parseInt(props.getProperty("db.pool.minIdle", "5")));
            
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery("SELECT 1");
            
            logger.info("Pool de connexions MySQL initialisé avec succès pour Loan");
            
        } catch (Exception e) {
            logger.error("Erreur lors de l'initialisation du pool de connexions", e);
            throw new RuntimeException("Erreur d'initialisation de la base de données", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Erreur lors de la fermeture de la connexion", e);
            }
        }
    }
}