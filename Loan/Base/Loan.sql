CREATE DATABASE IF NOT EXISTS loan_db;
USE loan_db;

CREATE TABLE pret (
   id_pret INT AUTO_INCREMENT PRIMARY KEY,
   montant_pret DECIMAL(15,2),
   type_pret VARCHAR(50),
   id_compte_courant INT, -- Référence INTERNE, pas de FK
   statut VARCHAR(20) DEFAULT 'ACTIF'
);

CREATE TABLE pret_values (
   id_pret_values INT AUTO_INCREMENT PRIMARY KEY,
   pourcentage_pret_values DECIMAL(15,2),
   duree_pret_values INT,
   id_pret INT,
   FOREIGN KEY(id_pret) REFERENCES pret(id_pret) -- FK INTERNE seulement
);