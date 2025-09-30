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