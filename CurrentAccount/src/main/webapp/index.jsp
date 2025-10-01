<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Current Account Microservice</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }
        h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        .endpoint { background: #f8f9fa; padding: 15px; margin: 10px 0; border-left: 4px solid #3498db; }
        .method { display: inline-block; padding: 5px 10px; border-radius: 3px; color: white; font-weight: bold; margin-right: 10px; }
        .get { background: #28a745; }
        .post { background: #007bff; }
        .put { background: #ffc107; color: black; }
        .delete { background: #dc3545; }
        code { background: #e9ecef; padding: 2px 5px; border-radius: 3px; }
    </style>
</head>
<body>
    <h1>🏦 Current Account Microservice</h1>
    <p>Microservice de gestion des comptes courants - API REST</p>
    
    <h2>📋 Endpoints disponibles</h2>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/compte-courant</code></strong>
        <p>Liste tous les comptes courants</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/compte-courant/{id}</code></strong>
        <p>Détails d'un compte spécifique</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/compte-courant/{id}/transactions</code></strong>
        <p>Transactions d'un compte spécifique</p>
    </div>
    
    <div class="endpoint">
        <span class="method post">POST</span>
        <strong><code>/api/compte-courant</code></strong>
        <p>Créer un nouveau compte courant</p>
    </div>
    
    <div class="endpoint">
        <span class="method put">PUT</span>
        <strong><code>/api/compte-courant/{id}</code></strong>
        <p>Mettre à jour le solde d'un compte</p>
    </div>
    
    <div class="endpoint">
        <span class="method delete">DELETE</span>
        <strong><code>/api/compte-courant/{id}</code></strong>
        <p>Supprimer un compte (marquer comme inactif)</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/transaction</code></strong>
        <p>Liste toutes les transactions</p>
    </div>
    
    <div class="endpoint">
        <span class="method get">GET</span>
        <strong><code>/api/transaction/compte/{idCompte}</code></strong>
        <p>Transactions d'un compte spécifique</p>
    </div>
    
    <div class="endpoint">
        <span class="method post">POST</span>
        <strong><code>/api/transaction</code></strong>
        <p>Créer une nouvelle transaction</p>
    </div>
    
    <h2>🔧 Informations techniques</h2>
    <ul>
        <li><strong>Port:</strong> 8081 (configurable dans le serveur)</li>
        <li><strong>Format:</strong> JSON</li>
        <li><strong>Encodage:</strong> UTF-8</li>
        <li><strong>CORS:</strong> Activé pour toutes les origines</li>
    </ul>
    
    <footer>
        <p>Microservice développé avec Java Servlets - Architecture Banking</p>
    </footer>
</body>
</html>