CREATE DATABASE IF NOT EXISTS currentaccount_db;

CREATE TABLE IF NOT EXISTS compte_courant (
   id_compte_courant INT AUTO_INCREMENT,
   solde_compte_courant DECIMAL(15,2),
   PRIMARY KEY(id_compte_courant)
);

CREATE TABLE IF NOT EXISTS transaction (
   id_transaction INT AUTO_INCREMENT,
   date_transaction DATE,
   montant_transaction DECIMAL(15,2) NOT NULL,
   type_transaction VARCHAR(50),
   id_compte_courant INT,
   PRIMARY KEY(id_transaction),
   FOREIGN KEY(id_compte_courant) REFERENCES compte_courant(id_compte_courant)
);