# Banking
Projet JAVA et C# qui utilise EJB et wildfly

-- Base pour CurrentAccount
CREATE DATABASE IF NOT EXISTS currentaccount_db;

-- Base pour Loan  
CREATE DATABASE IF NOT EXISTS loan_db;

-- Base pour DepositAccount
CREATE DATABASE IF NOT EXISTS depositaccount_db;

-- Base pour Central (optionnelle)
CREATE DATABASE IF NOT EXISTS central_db;

CurrentAccount :
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/CurrentAccount"
mvn clean compile
mvn package

Loan :
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Loan"
mvn clean compile
mvn package

DepositAccount :
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/DepositAccount"
dotnet build
dotnet publish -c Release -o ./publish

Central :
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Central"
mvn clean compile
mvn package

Deployement sur wildfly :
# Naviguer vers le dossier WildFly
cd "/chemin/vers/wildfly-37.0.1.Final/bin"

# Démarrer WildFly
./standalone.sh
# Ou sur Windows:
standalone.bat

Déployer les WARs :
# Copier CurrentAccount
cp "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/CurrentAccount/target/currentaccount.war" "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"

# Copier Loan
cp "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Loan/target/loan.war" "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"

# Copier Central
cp "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Central/target/central.war" "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"

OU via l'interface web WildFly :
http://localhost:9990/console/App.html

LANCEMENT DU MICROSERVICE .NET :
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/DepositAccount"
dotnet run --urls="http://localhost:8084"

OU depuis le dossier publish :
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/DepositAccount/publish"
dotnet DepositAccount.dll --urls="http://localhost:8084"

VÉRIFICATION DES SERVICES :

CurrentAccount (Port 8081) :
curl -X GET "http://localhost:8081/currentaccount/api/compte-courant"
http://localhost:8081/currentaccount/

Loan (Port 8082) :
curl -X GET "http://localhost:8082/loan/api/pret"
http://localhost:8082/loan/

DepositAccount (Port 8084) :
curl -X GET "http://localhost:8084/api/depot"
http://localhost:8084/

Central (Port 8080) :
curl -X GET "http://localhost:8080/central/api/central/health"
http://localhost:8080/central/

COMMANDES RAPIDES POUR LE DÉVELOPPEMENT :

Redéploiement rapide d'un microservice :
# Recompiler et redéployer CurrentAccount
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/CurrentAccount"
mvn clean package -DskipTests
cp target/currentaccount.war "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"

Vérifier les logs WildFly :
tail -f "/chemin/vers/wildfly-37.0.1.Final/standalone/log/server.log"

Liste des applications déployées :
curl -X GET "http://localhost:9990/management" --user admin:password