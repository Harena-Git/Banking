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