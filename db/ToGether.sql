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

CREATE DATABASE IF NOT EXISTS depositaccount_db;
USE depositaccount_db;

CREATE TABLE depot (
   id_depot INT AUTO_INCREMENT PRIMARY KEY,
   date_depot DATE NOT NULL,
   montant_depot DECIMAL(15,2) NOT NULL,
   type_depot VARCHAR(50),
   id_compte_courant INT, -- Référence, pas de FK
   id_historique_local INT -- Historique INTERNE
);

CREATE TABLE interet (
   id_interet INT AUTO_INCREMENT PRIMARY KEY,
   pourcentage_interet DECIMAL(15,2),
   durree_interet INT,
   id_depot INT,
   FOREIGN KEY(id_depot) REFERENCES depot(id_depot) -- FK INTERNE seulement
);

CREATE DATABASE IF NOT EXISTS central_db;
USE central_db;

-- Table de référence (cache/aggregation)
CREATE TABLE banque (
   id_banque INT AUTO_INCREMENT PRIMARY KEY,
   nom_banque VARCHAR(50) UNIQUE,
   localisation_banque VARCHAR(50)
);

-- Table d'aggrégation (pas de FK vers autres services)
CREATE TABLE vue_globale_compte (
   id_compte_courant INT PRIMARY KEY,
   solde_actuel DECIMAL(15,2),
   total_prets DECIMAL(15,2),
   total_depots DECIMAL(15,2),
   derniere_maj TIMESTAMP
);