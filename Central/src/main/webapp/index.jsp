<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Central Microservice</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }
        h1 { color: #2c3e50; border-bottom: 2px solid #e74c3c; padding-bottom: 10px; }
        .endpoint { background: #f8f9fa; padding: 15px; margin: 10px 0; border-left: 4px solid #e74c3c; }
        .method { display: inline-block; padding: 5px 10px; border-radius: 3px; color: white; font-weight: bold; margin-right: 10px; }
        .get { background: #28a745; }
        code { background: #e9ecef; padding: 2px 5px; border-radius: 3px; }
        .microservice { background: #fff3cd; padding: 10px; margin: 5px 0; border-radius: 5px; }
    </style>
</head>
<body>
    <h1>🎯 Central Microservice</h1>
    <p>Service d'agrégation central pour le système bancaire</p>
    
    <h2>🔄 Microservices connectés</h2>
    <div class="microservice">
        <strong>CurrentAccount:</strong> Gestion des comptes courants et transactions
    </div>
    <div class="microservice">
        <strong>Loan:</strong> Gestion des prêts et taux d'intérêt
    </div>
    <div class="microservice">
        <strong>DepositAccount:</strong> Gestion des dépôts et intérêts
    </div>
    
    <h2>📋 Endpoints disponibles</h2>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/central</code></strong>
        <p>Statut du service central</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/central/health</code></strong>
        <p>Santé de tous les microservices</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/central/comptes</code></strong>
        <p>Liste de tous les comptes</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/central/compte/{id}</code></strong>
        <p>Détails complets d'un compte (solde + prêts + dépôts)</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/central/compte/{id}/prets</code></strong>
        <p>Prêts d'un compte spécifique</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/central/compte/{id}/depots</code></strong>
        <p>Dépôts d'un compte spécifique</p>
    </div>
    
    <h2>🔧 Informations techniques</h2>
    <ul>
        <li><strong>Port:</strong> 8080 (configurable dans le serveur)</li>
        <li><strong>Format:</strong> JSON</li>
        <li><strong>Architecture:</strong> Microservices avec agrégation</li>
        <li><strong>CORS:</strong> Activé pour toutes les origines</li>
    </ul>
    
    <footer>
        <p>Service central développé avec Java Servlets - Architecture Banking Microservices</p>
    </footer>
</body>
</html>