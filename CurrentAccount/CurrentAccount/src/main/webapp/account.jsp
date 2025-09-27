<%-- Banking/CurrentAccount/src/main/webapp/account.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Compte Courant</title>
</head>
<body>
    <h1>Solde: ${solde}</h1>

    <h2>Historique des Transactions</h2>
    <ul>
        <c:forEach var="trans" items="${transactions}">
            <li>${trans.typeTransaction} - ${trans.montant} le ${trans.dateTransaction}</li>
        </c:forEach>
    </ul>

    <form action="currentaccount" method="post">
        <input type="hidden" name="action" value="depot">
        <input type="hidden" name="idCompte" value="${param.idCompte}">
        Montant: <input type="text" name="montant">
        <input type="submit" value="Dépot">
    </form>

    <!-- Formulaires similaires pour retrait et virement -->
    <form action="currentaccount" method="post">
        <input type="hidden" name="action" value="retrait">
        <input type="hidden" name="idCompte" value="${param.idCompte}">
        Montant: <input type="text" name="montant">
        <input type="submit" value="Retrait">
    </form>

    <form action="currentaccount" method="post">
        <input type="hidden" name="action" value="virement">
        <input type="hidden" name="idCompte" value="${param.idCompte}">
        ID Compte Dest: <input type="text" name="idCompteDest">
        Montant: <input type="text" name="montant">
        <input type="submit" value="Virement">
    </form>
</body>
</html>