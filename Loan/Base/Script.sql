CREATE TABLE pret (
   id_pret INT AUTO_INCREMENT,
   montant_pret DECIMAL(15,2),
   type_pret VARCHAR(50),
   PRIMARY KEY(id_pret)
);

CREATE TABLE pret_values (
   id_pret_values INT AUTO_INCREMENT,
   pourcentage_pret_values DECIMAL(15,2),
   duree_pret_values INT,
   id_pret INT NOT NULL,
   PRIMARY KEY(id_pret_values),
   FOREIGN KEY(id_pret) REFERENCES pret(id_pret)
);