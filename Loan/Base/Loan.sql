CREATE DATABASE IF NOT EXISTS loan_db;
USE loan_db;

CREATE TABLE pret (
   id_pret INT AUTO_INCREMENT PRIMARY KEY,
   montant_pret DECIMAL(15,2) NOT NULL,
   type_pret VARCHAR(50) NOT NULL,
   id_compte_courant INT NOT NULL,
   date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   statut ENUM('EN_ATTENTE', 'APPROUVE', 'REJETE', 'REMBOURSE') DEFAULT 'EN_ATTENTE',
   date_debut DATE,
   date_fin DATE,
   INDEX idx_compte (id_compte_courant),
   INDEX idx_statut (statut)
);

CREATE TABLE pret_values (
   id_pret_values INT AUTO_INCREMENT PRIMARY KEY,
   pourcentage_pret_values DECIMAL(5,2) NOT NULL,
   duree_pret_values INT NOT NULL,
   id_pret INT NOT NULL,
   type_taux ENUM('FIXE', 'VARIABLE') DEFAULT 'FIXE',
   date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY(id_pret) REFERENCES pret(id_pret) ON DELETE CASCADE,
   INDEX idx_pret (id_pret)
);

-- Données de test
INSERT INTO pret (montant_pret, type_pret, id_compte_courant, statut, date_debut, date_fin) VALUES 
(5000.00, 'PERSONNEL', 1, 'APPROUVE', '2024-01-01', '2025-01-01'),
(15000.00, 'IMMOBILIER', 2, 'EN_ATTENTE', NULL, NULL),
(3000.00, 'AUTO', 1, 'REMBOURSE', '2023-06-01', '2024-06-01');

INSERT INTO pret_values (pourcentage_pret_values, duree_pret_values, id_pret, type_taux) VALUES 
(3.5, 12, 1, 'FIXE'),
(2.8, 24, 2, 'VARIABLE'),
(4.2, 12, 3, 'FIXE');