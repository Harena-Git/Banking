-- Script pour la base de données du projet Loan (Prêt)
CREATE DATABASE loan_db;
USE loan_db;

CREATE TABLE client (
   id_client INT PRIMARY KEY,
   nom VARCHAR(100) NOT NULL,
   prenom VARCHAR(100),
   adresse VARCHAR(255),
   date_naissance DATE
);

CREATE TABLE banque (
   id_banque INT PRIMARY KEY,
   nom_banque VARCHAR(100) UNIQUE NOT NULL,
   localisation VARCHAR(100)
);

CREATE TABLE compte (
   id_compte INT PRIMARY KEY,
   id_client INT NOT NULL,
   id_banque INT NOT NULL,
   type_compte VARCHAR(20) CHECK (type_compte IN ('pret')),
   solde DECIMAL(15,2) DEFAULT 0,
   FOREIGN KEY (id_client) REFERENCES client(id_client),
   FOREIGN KEY (id_banque) REFERENCES banque(id_banque)
);

CREATE TABLE transaction (
   id_transaction INT PRIMARY KEY,
   id_compte INT NOT NULL,
   date_transaction DATE NOT NULL,
   montant DECIMAL(15,2) NOT NULL,
   type_transaction VARCHAR(20) CHECK (type_transaction IN ('depot','retrait','virement','remboursement')),
   FOREIGN KEY (id_compte) REFERENCES compte(id_compte)
);

CREATE TABLE pret (
   id_pret INT PRIMARY KEY,
   id_compte INT NOT NULL, -- compte lié au prêt
   montant DECIMAL(15,2) NOT NULL,
   taux_interet DECIMAL(5,2) NOT NULL,
   duree_mois INT NOT NULL,
   date_octroi DATE NOT NULL,
   FOREIGN KEY (id_compte) REFERENCES compte(id_compte)
);

CREATE TABLE interet (
   id_interet INT PRIMARY KEY,
   id_compte INT,
   id_pret INT,
   taux DECIMAL(5,2) NOT NULL,
   duree_mois INT,
   FOREIGN KEY (id_compte) REFERENCES compte(id_compte),
   FOREIGN KEY (id_pret) REFERENCES pret(id_pret)
);