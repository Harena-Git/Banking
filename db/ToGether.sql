-- Central
CREATE TABLE banque (
   id_banque INT AUTO_INCREMENT,
   nom_banque VARCHAR(50),
   localisation_banque VARCHAR(50),
   PRIMARY KEY(id_banque),
   UNIQUE(nom_banque)
);

CREATE TABLE historique_generaliser (
   id_historique INT AUTO_INCREMENT,
   date_transaction_historique DATE NOT NULL,
   montant_jouee_historique DECIMAL(15,2) NOT NULL,
   type_transaction VARCHAR(50),
   PRIMARY KEY(id_historique)
);

CREATE TABLE compte_banque (
   id_banque INT,
   id_compte_courant INT,
   PRIMARY KEY(id_banque, id_compte_courant),
   FOREIGN KEY(id_banque) REFERENCES banque(id_banque)
);

CREATE TABLE compte_pret (
   id_compte_courant INT,
   id_pret INT,
   date_pret DATE,
   PRIMARY KEY(id_compte_courant, id_pret)
);

CREATE TABLE pret_historique (
   id_historique INT,
   id_pret INT,
   PRIMARY KEY(id_historique, id_pret),
   FOREIGN KEY(id_historique) REFERENCES historique_generaliser(id_historique)
);

-- CurrentAccount
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

-- Loan
CREATE TABLE IF NOT EXISTS pret (
   id_pret INT AUTO_INCREMENT,
   montant_pret DECIMAL(15,2),
   type_pret VARCHAR(50),
   PRIMARY KEY(id_pret)
);

CREATE TABLE IF NOT EXISTS pret_values (
   id_pret_values INT AUTO_INCREMENT,
   pourcentage_pret_values DECIMAL(15,2),
   duree_pret_values INT,
   id_pret INT NOT NULL,
   PRIMARY KEY(id_pret_values),
   FOREIGN KEY(id_pret) REFERENCES pret(id_pret)
);

-- DepositAccount
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