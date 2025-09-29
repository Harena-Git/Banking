CREATE TABLE Clients(
   id_client INTEGER,
   nom VARCHAR(50)  NOT NULL,
   prenom VARCHAR(50)  NOT NULL,
   date_naissance DATE NOT NULL,
   adresse VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   numero_telephone VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_client),
   UNIQUE(email),
   UNIQUE(numero_telephone)
);

CREATE TABLE Types_Compte(
   id_type_compte INTEGER,
   type VARCHAR(50)  NOT NULL,
   frais_tenue_compte NUMERIC(15,2)   NOT NULL,
   frais_carte NUMERIC(15,2)   NOT NULL,
   frais_decouvert NUMERIC(15,2)   NOT NULL,
   frais_retrait_etranger NUMERIC(15,2)   NOT NULL,
   frais_international NUMERIC(15,2)   NOT NULL,
   decouvert_autorise NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_type_compte),
   UNIQUE(type)
);

CREATE TABLE Types_Transaction(
   id_type_transaction INTEGER,
   type VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type_transaction),
   UNIQUE(type)
);

CREATE TABLE Types_Pret(
   id_type_pret INTEGER,
   type VARCHAR(50)  NOT NULL,
   taux NUMERIC(5,4)   NOT NULL,
   duree INTEGER NOT NULL,
   montant NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_type_pret),
   UNIQUE(type)
);

CREATE TABLE Types_Epargne(
   id_type_epargne INTEGER,
   type VARCHAR(50)  NOT NULL,
   taux NUMERIC(5,4)   NOT NULL,
   versement_minimum NUMERIC(15,2)   NOT NULL,
   illimite BOOLEAN NOT NULL,
   PRIMARY KEY(id_type_epargne),
   UNIQUE(type)
);

CREATE TABLE Types_Penalite_Epargne(
   id_type_penalite INTEGER,
   type VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type_penalite),
   UNIQUE(type)
);

CREATE TABLE Types_Penalite_Pret(
   id_type_penalite_pret INTEGER,
   type VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type_penalite_pret),
   UNIQUE(type)
);

CREATE TABLE Statuts_Compte(
   id_statut INTEGER,
   statut VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_statut),
   UNIQUE(statut)
);

CREATE TABLE Types_Carte(
   id_type_carte INTEGER,
   type VARCHAR(50)  NOT NULL,
   validite INTEGER NOT NULL,
   PRIMARY KEY(id_type_carte),
   UNIQUE(type)
);

CREATE TABLE Types_Commission(
   id_type_commission INTEGER,
   type VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type_commission),
   UNIQUE(type)
);

CREATE TABLE Frais_Exceptionnels(
   id_frais INTEGER,
   montant INTEGER NOT NULL,
   libelle VARCHAR(50)  NOT NULL,
   id_type_compte INTEGER NOT NULL,
   PRIMARY KEY(id_frais),
   FOREIGN KEY(id_type_compte) REFERENCES Types_Compte(id_type_compte)
);

CREATE TABLE Statuts_Pret(
   id_status_pret INTEGER,
   statut VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_status_pret),
   UNIQUE(statut)
);

CREATE TABLE Comptes(
   id_compte INTEGER,
   date_inscription DATE NOT NULL,
   id_statut INTEGER NOT NULL,
   id_client INTEGER NOT NULL,
   PRIMARY KEY(id_compte),
   FOREIGN KEY(id_statut) REFERENCES Statuts_Compte(id_statut),
   FOREIGN KEY(id_client) REFERENCES Clients(id_client)
);

CREATE TABLE Prets(
   id_pret INTEGER,
   id_compte INTEGER NOT NULL,
   id_type_pret INTEGER NOT NULL,
   PRIMARY KEY(id_pret),
   FOREIGN KEY(id_compte) REFERENCES Comptes(id_compte),
   FOREIGN KEY(id_type_pret) REFERENCES Types_Pret(id_type_pret)
);

CREATE TABLE Comptes_Epargnes(
   id_compte_epargne INTEGER,
   numero VARCHAR(50)  NOT NULL,
   id_type_epargne INTEGER NOT NULL,
   id_compte INTEGER NOT NULL,
   PRIMARY KEY(id_compte_epargne),
   UNIQUE(numero),
   FOREIGN KEY(id_type_epargne) REFERENCES Types_Epargne(id_type_epargne),
   FOREIGN KEY(id_compte) REFERENCES Comptes(id_compte)
);

CREATE TABLE Penalites_Pret(
   id_penalite_pret INTEGER,
   pourcentage NUMERIC(15,2)   NOT NULL,
   id_type_penalite_pret INTEGER NOT NULL,
   id_pret INTEGER NOT NULL,
   PRIMARY KEY(id_penalite_pret),
   FOREIGN KEY(id_type_penalite_pret) REFERENCES Types_Penalite_Pret(id_type_penalite_pret),
   FOREIGN KEY(id_pret) REFERENCES Prets(id_pret)
);

CREATE TABLE Penalites_Epargne(
   id_penalite_epargne INTEGER,
   pourcentage NUMERIC(15,2)   NOT NULL,
   id_type_penalite INTEGER NOT NULL,
   id_compte_epargne INTEGER NOT NULL,
   PRIMARY KEY(id_penalite_epargne),
   FOREIGN KEY(id_type_penalite) REFERENCES Types_Penalite_Epargne(id_type_penalite),
   FOREIGN KEY(id_compte_epargne) REFERENCES Comptes_Epargnes(id_compte_epargne)
);

CREATE TABLE Commissions(
   id_commission INTEGER,
   montant NUMERIC(15,2)   NOT NULL,
   id_type_commission INTEGER NOT NULL,
   id_type_carte INTEGER NOT NULL,
   PRIMARY KEY(id_commission),
   FOREIGN KEY(id_type_commission) REFERENCES Types_Commission(id_type_commission),
   FOREIGN KEY(id_type_carte) REFERENCES Types_Carte(id_type_carte)
);
   
CREATE TABLE Soldes_Epargne(
   id_solde_epargne INTEGER,
   solde NUMERIC(15,2)   NOT NULL,
   interets_cumules NUMERIC(15,2)   NOT NULL,
   date_dernier_calcul DATE NOT NULL,
   id_compte_epargne INTEGER NOT NULL,
   PRIMARY KEY(id_solde_epargne),
   FOREIGN KEY(id_compte_epargne) REFERENCES Comptes_Epargnes(id_compte_epargne)
);

CREATE TABLE Etat_Prets(
   id_etat_pret INTEGER,
   capital_restant NUMERIC(15,2)   NOT NULL,
   interets_restants NUMERIC(15,2)   NOT NULL,
   prochaine_echeance DATE NOT NULL,
   id_pret INTEGER NOT NULL,
   id_status_pret INTEGER NOT NULL,
   PRIMARY KEY(id_etat_pret),
   FOREIGN KEY(id_pret) REFERENCES Prets(id_pret),
   FOREIGN KEY(id_status_pret) REFERENCES Statuts_Pret(id_status_pret)
);

CREATE TABLE Comptes_Courants(
   id_compte_courant INTEGER,
   numero VARCHAR(50)  NOT NULL,
   id_compte INTEGER NOT NULL,
   id_type_compte INTEGER NOT NULL,
   PRIMARY KEY(id_compte_courant),
   UNIQUE(id_compte),
   UNIQUE(numero),
   FOREIGN KEY(id_compte) REFERENCES Comptes(id_compte),
   FOREIGN KEY(id_type_compte) REFERENCES Types_Compte(id_type_compte)
);

CREATE TABLE Histo_Transactions(
   id_histo_transaction INTEGER,
   montant NUMERIC(15,2)   NOT NULL,
   date_ DATE NOT NULL,
   libelle VARCHAR(100)  NOT NULL,
   id_compte_courant INTEGER NOT NULL,
   id_type_transaction INTEGER NOT NULL,
   PRIMARY KEY(id_histo_transaction),
   FOREIGN KEY(id_compte_courant) REFERENCES Comptes_Courants(id_compte_courant),
   FOREIGN KEY(id_type_transaction) REFERENCES Types_Transaction(id_type_transaction)
);

CREATE TABLE Histo_Epargne(
   id_histo_epargne INTEGER,
   montant NUMERIC(15,2)   NOT NULL,
   date_depot DATE NOT NULL,
   id_compte_epargne INTEGER NOT NULL,
   PRIMARY KEY(id_histo_epargne),
   FOREIGN KEY(id_compte_epargne) REFERENCES Comptes_Epargnes(id_compte_epargne)
);

CREATE TABLE Histo_Paiements_Pret(
   id_histo_paiement_pret INTEGER,
   montant NUMERIC(15,2)   NOT NULL,
   date_paiement DATE NOT NULL,
   id_pret INTEGER NOT NULL,
   PRIMARY KEY(id_histo_paiement_pret),
   FOREIGN KEY(id_pret) REFERENCES Prets(id_pret)
);

CREATE TABLE Cartes(
   id_carte INTEGER,
   numero VARCHAR(50)  NOT NULL,
   date_acquisition DATE NOT NULL,
   id_compte_courant INTEGER NOT NULL,
   id_type_carte INTEGER NOT NULL,
   PRIMARY KEY(id_carte),
   UNIQUE(id_compte_courant),
   UNIQUE(numero),
   FOREIGN KEY(id_compte_courant) REFERENCES Comptes_Courants(id_compte_courant),
   FOREIGN KEY(id_type_carte) REFERENCES Types_Carte(id_type_carte)
);

CREATE TABLE Soldes_Comptes(
   id_solde_compte INTEGER,
   solde_disponible NUMERIC(15,2)   NOT NULL,
   date_derniere_operation DATE NOT NULL,
   id_compte_courant INTEGER NOT NULL,
   PRIMARY KEY(id_solde_compte),
   FOREIGN KEY(id_compte_courant) REFERENCES Comptes_Courants(id_compte_courant)
);
