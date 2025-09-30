CREATE DATABASE IF NOT EXISTS currentaccount_db;
USE currentaccount_db;

CREATE TABLE compte_courant (
   id_compte_courant INT AUTO_INCREMENT PRIMARY KEY,
   solde_compte_courant DECIMAL(15,2) DEFAULT 0
);

CREATE TABLE transaction (
   id_transaction INT AUTO_INCREMENT PRIMARY KEY,
   date_transaction DATE,
   montant_transaction DECIMAL(15,2) NOT NULL,
   type_transaction VARCHAR(50),
   id_compte_courant INT
   -- PAS de FOREIGN KEY vers d'autres microservices
);