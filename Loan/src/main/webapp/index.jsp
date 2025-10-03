<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Microservice</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }
        h1 { color: #2c3e50; border-bottom: 2px solid #e74c3c; padding-bottom: 10px; }
        .endpoint { background: #f8f9fa; padding: 15px; margin: 10px 0; border-left: 4px solid #e74c3c; }
        .method { display: inline-block; padding: 5px 10px; border-radius: 3px; color: white; font-weight: bold; margin-right: 10px; }
        .get { background: #28a745; }
        .post { background: #007bff; }
        .put { background: #ffc107; color: black; }
        .delete { background: #dc3545; }
        code { background: #e9ecef; padding: 2px 5px; border-radius: 3px; }
        .section { margin-top: 30px; }
    </style>
</head>
<body>
    <h1>💰 Loan Microservice</h1>
    <p>Microservice de gestion des prêts bancaires - API REST</p>
    
    <div class="section">
        <h2>📋 Gestion des Prêts</h2>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret</code></strong>
            <p>Liste tous les prêts</p>
        </div>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret/{id}</code></strong>
            <p>Détails d'un prêt spécifique</p>
        </div>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret/compte/{idCompte}</code></strong>
            <p>Prêts d'un compte spécifique</p>
        </div>
        
        <div class="endpoint">
            <span class="method post">POST</span>
            <strong><code>/api/pret</code></strong>
            <p>Créer un nouveau prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method put">PUT</span>
            <strong><code>/api/pret/{id}</code></strong>
            <p>Mettre à jour le statut d'un prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method delete">DELETE</span>
            <strong><code>/api/pret/{id}</code></strong>
            <p>Supprimer un prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret/{id}/taux</code></strong>
            <p>Taux d'un prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret/{id}/taux-actuel</code></strong>
            <p>Taux actuel d'un prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret/{id}/mensualite</code></strong>
            <p>Calcul de la mensualité d'un prêt</p>
        </div>
    </div>
    
    <div class="section">
        <h2>📊 Gestion des Taux</h2>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret-values/pret/{idPret}</code></strong>
            <p>Configurations de taux d'un prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method get">GET</span>
            <strong><code>/api/pret-values/pret/{idPret}/actuel</code></strong>
            <p>Configuration de taux actuelle d'un prêt</p>
        </div>
        
        <div class="endpoint">
            <span class="method post">POST</span>
            <strong><code>/api/pret-values</code></strong>
            <p>Créer une nouvelle configuration de taux</p>
        </div>
        
        <div class="endpoint">
            <span class="method put">PUT</span>
            <strong><code>/api/pret-values/{id}</code></strong>
            <p>Mettre à jour une configuration de taux</p>
        </div>
        
        <div class="endpoint">
            <span class="method delete">DELETE</span>
            <strong><code>/api/pret-values/{id}</code></strong>
            <p>Supprimer une configuration de taux</p>
        </div>
    </div>
    
    <h2>🔧 Informations techniques</h2>
    <ul>
        <li><strong>Port:</strong> 8082 (configurable dans le serveur)</li>
        <li><strong>Format:</strong> JSON</li>
        <li><strong>Encodage:</strong> UTF-8</li>
        <li><strong>CORS:</strong> Activé pour toutes les origines</li>
        <li><strong>Statuts de prêt:</strong> EN_ATTENTE, APPROUVE, REJETE, REMBOURSE</li>
        <li><strong>Types de taux:</strong> FIXE, VARIABLE</li>
    </ul>
    
    <h2>🧮 Fonctionnalités avancées</h2>
    <ul>
        <li><strong>Calcul de mensualité:</strong> Formule de calcul avec taux d'intérêt</li>
        <li><strong>Gestion des taux:</strong> Historique des taux par prêt</li>
        <li><strong>Workflow de prêt:</strong> Gestion des statuts</li>
        <li><strong>Association compte:</strong> Liaison avec les comptes courants</li>
    </ul>
    
    <footer>
        <p>Microservice développé avec Java Servlets - Architecture Banking</p>
    </footer>
</body>
</html>