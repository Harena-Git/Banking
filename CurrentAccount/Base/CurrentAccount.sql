CREATE DATABASE IF NOT EXISTS currentaccount_db;
USE currentaccount_db;

CREATE TABLE compte_courant (
   id_compte_courant INT AUTO_INCREMENT PRIMARY KEY,
   solde_compte_courant DECIMAL(15,2) DEFAULT 0.00,
   date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   statut ENUM('ACTIF', 'INACTIF', 'BLOQUE') DEFAULT 'ACTIF'
);

CREATE TABLE transaction (
   id_transaction INT AUTO_INCREMENT PRIMARY KEY,
   date_transaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   montant_transaction DECIMAL(15,2) NOT NULL,
   type_transaction ENUM('DEPOT', 'RETRAIT', 'VIREMENT', 'FRAIS') NOT NULL,
   id_compte_courant INT NOT NULL,
   description VARCHAR(255),
   compte_destinataire INT NULL, -- Pour virements
   INDEX idx_compte (id_compte_courant),
   INDEX idx_date (date_transaction)
);

-- Données de test
INSERT INTO compte_courant (solde_compte_courant) VALUES 
(1000.00),
(2500.50),
(500.00);

INSERT INTO transaction (montant_transaction, type_transaction, id_compte_courant, description) VALUES 
(100.00, 'DEPOT', 1, 'Dépôt initial'),
(-50.00, 'RETRAIT', 1, 'Retrait guichet'),
(200.00, 'VIREMENT', 2, 'Virement salaire');