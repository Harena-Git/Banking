USE currentaccount_db;

-- Comptes courants
INSERT INTO compte_courant (solde_compte_courant) VALUES
(1500.00),
(2500.50),
(500.00);

-- Transactions (référence au compte, mais sans FK inter-microservices)
INSERT INTO transaction (date_transaction, montant_transaction, type_transaction, id_compte_courant) VALUES
('2025-01-10', 200.00, 'DEPOT', 1),
('2025-01-12', -100.00, 'RETRAIT', 1),
('2025-02-01', 500.00, 'DEPOT', 2),
('2025-02-03', -50.00, 'RETRAIT', 3);

USE loan_db;

-- Prêts
INSERT INTO pret (montant_pret, type_pret, id_compte_courant, statut) VALUES
(10000.00, 'PERSONNEL', 1, 'ACTIF'),
(20000.00, 'IMMOBILIER', 2, 'ACTIF'),
(5000.00, 'CONSOMMATION', 3, 'REMBOURSE');

-- Valeurs associées à chaque prêt
INSERT INTO pret_values (pourcentage_pret_values, duree_pret_values, id_pret) VALUES
(5.00, 24, 1),
(3.50, 120, 2),
(6.00, 12, 3);

USE depositaccount_db;

-- Dépôts
INSERT INTO depot (date_depot, montant_depot, type_depot, id_compte_courant, id_historique_local) VALUES
('2025-01-05', 1000.00, 'DEPOT_INITIAL', 1, 101),
('2025-01-15', 2000.00, 'DEPOT_INITIAL', 2, 102),
('2025-02-01', 300.00, 'DEPOT_MENSUEL', 1, 103),
('2025-02-10', 150.00, 'DEPOT_MENSUEL', 3, 104);

-- Intérêts liés aux dépôts
INSERT INTO interet (pourcentage_interet, durree_interet, id_depot) VALUES
(2.50, 12, 1),
(3.00, 24, 2),
(2.75, 6, 3),
(2.00, 12, 4);

USE central_db;

-- Banques
INSERT INTO banque (nom_banque, localisation_banque) VALUES
('Banque Centrale', 'Paris'),
('Banque Populaire', 'Lyon'),
('Banque Rurale', 'Marseille');

-- Vue globale des comptes (aggrégation simulée)
INSERT INTO vue_globale_compte (id_compte_courant, solde_actuel, total_prets, total_depots, derniere_maj) VALUES
(1, 1500.00, 10000.00, 1300.00, NOW()),
(2, 2500.50, 20000.00, 2000.00, NOW()),
(3, 500.00, 5000.00, 150.00, NOW());

