-- Central n'a pas besoin de tables complexes
-- Il agrège les données des autres microservices via API

CREATE DATABASE IF NOT EXISTS central_db;
USE central_db;

-- Table de cache/agrégation optionnelle pour les performances
CREATE TABLE IF NOT EXISTS cache_compte_details (
    id_compte INT PRIMARY KEY,
    solde_actuel DECIMAL(15,2),
    total_prets DECIMAL(15,2),
    total_depots DECIMAL(15,2),
    derniere_maj TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_derniere_maj (derniere_maj)
);

-- Table de logs des appels inter-services
CREATE TABLE IF NOT EXISTS logs_appels (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    microservice_cible VARCHAR(50),
    endpoint VARCHAR(255),
    statut_code INT,
    temps_reponse_ms INT,
    date_appel TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_microservice (microservice_cible),
    INDEX idx_date (date_appel)
);