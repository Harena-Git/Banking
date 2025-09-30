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