CREATE DATABASE IF NOT EXISTS depositaccount_db;

USE depositaccount_db;

CREATE TABLE IF NOT EXISTS historique_generaliser(
   id_historique INT AUTO_INCREMENT,
   date_transaction_historique DATE NOT NULL,
   montant_jouee_historique DECIMAL(15,2) NOT NULL,
   type_transaction VARCHAR(50),
   PRIMARY KEY(id_historique)
);

CREATE TABLE IF NOT EXISTS depot(
   id_depot INT AUTO_INCREMENT,
   date_depot DATE NOT NULL,
   montant_depot DECIMAL(15,2) NOT NULL,
   type_depot VARCHAR(50),
   id_historique INT,
   id_compte_courant INT,
   PRIMARY KEY(id_depot),
   FOREIGN KEY(id_historique) REFERENCES historique_generaliser(id_historique)
);

CREATE TABLE IF NOT EXISTS interet(
   id_interet INT AUTO_INCREMENT,
   pourcentage_interet DECIMAL(15,2),
   durree_interet INT,
   id_depot INT NOT NULL,
   PRIMARY KEY(id_interet),
   FOREIGN KEY(id_depot) REFERENCES depot(id_depot)
);