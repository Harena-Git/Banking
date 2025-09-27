// Banking/CurrentAccount/src/main/java/com/banking/servlet/CurrentAccountServlet.java
package com.banking.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import com.banking.ejb.CurrentAccountRemote;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/currentaccount")
public class CurrentAccountServlet extends HttpServlet {

    @EJB
    private CurrentAccountRemote currentAccountBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int idCompte = Integer.parseInt(request.getParameter("idCompte"));

        if ("solde".equals(action)) {
            BigDecimal solde = currentAccountBean.getSolde(idCompte);
            request.setAttribute("solde", solde);
            request.getRequestDispatcher("/account.jsp").forward(request, response);
        } else if ("historique".equals(action)) {
            request.setAttribute("transactions", currentAccountBean.getHistoriqueTransactions(idCompte));
            request.getRequestDispatcher("/account.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int idCompte = Integer.parseInt(request.getParameter("idCompte"));
        BigDecimal montant = new BigDecimal(request.getParameter("montant"));

        if ("depot".equals(action)) {
            currentAccountBean.depot(idCompte, montant);
        } else if ("retrait".equals(action)) {
            currentAccountBean.retrait(idCompte, montant);
        } else if ("virement".equals(action)) {
            int idCompteDest = Integer.parseInt(request.getParameter("idCompteDest"));
            currentAccountBean.virement(idCompte, idCompteDest, montant);
        }

        response.sendRedirect("currentaccount?action=solde&idCompte=" + idCompte);
    }
}