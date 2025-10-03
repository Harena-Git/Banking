#!/bin/bash

echo "🚀 Déploiement du système Banking Microservices"

# Arrêter WildFly s'il est en cours d'exécution
echo "🛑 Arrêt de WildFly..."
pkill -f wildfly

# Attendre l'arrêt
sleep 5

# Démarrer WildFly
echo "🔥 Démarrage de WildFly..."
cd "/chemin/vers/wildfly-37.0.1.Final/bin"
./standalone.sh &
echo "⏳ Attente du démarrage de WildFly..."
sleep 30

# Compiler les microservices Java
echo "🔨 Compilation des microservices Java..."

echo "📦 CurrentAccount..."
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/CurrentAccount"
mvn clean package -DskipTests

echo "📦 Loan..."
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Loan"
mvn clean package -DskipTests

echo "📦 Central..."
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Central"
mvn clean package -DskipTests

# Déployer sur WildFly
echo "🚀 Déploiement sur WildFly..."

cp "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/CurrentAccount/target/currentaccount.war" "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"
cp "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Loan/target/loan.war" "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"
cp "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/Central/target/central.war" "/chemin/vers/wildfly-37.0.1.Final/standalone/deployments/"

echo "⏳ Attente du déploiement..."
sleep 15

# Lancer le microservice .NET
echo "🔵 Démarrage du microservice .NET..."
cd "/D:/Cours/Architecture_Mr_Tahina/Sujet 1 Banking/DepositAccount"
dotnet run --urls="http://localhost:8084" &

echo "✅ Déploiement terminé!"
echo "🌐 URLs des services:"
echo "   CurrentAccount: http://localhost:8081/currentaccount"
echo "   Loan:           http://localhost:8082/loan" 
echo "   DepositAccount: http://localhost:8084"
echo "   Central:        http://localhost:8080/central"