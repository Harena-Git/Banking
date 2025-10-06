package com.example.banking.central.client;

import com.example.banking.loan.ejb.PretEJBRemote;
import com.example.banking.loan.model.Pret;
import com.example.banking.central.model.PretInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.naming.Context;
import jakarta.naming.InitialContext;
import jakarta.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LoanEJBClient {
    private static final Logger logger = LoggerFactory.getLogger(LoanEJBClient.class);
    private PretEJBRemote pretEJB;
    private final Properties config;

    public LoanEJBClient(Properties config) {
        this.config = config;
        initEJB();
    }

    private void initEJB() {
        try {
            Properties jndiProps = new Properties();
            jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            jndiProps.put(Context.SECURITY_PRINCIPAL, "admin");
            jndiProps.put(Context.SECURITY_CREDENTIALS, "admin");

            Context context = new InitialContext(jndiProps);

            // JNDI Lookup pour l'EJB remote de Loan
            String jndiName = "ejb:/loan/PretEJB!com.example.banking.loan.ejb.PretEJBRemote";
            pretEJB = (PretEJBRemote) context.lookup(jndiName);

            logger.info("EJB Loan connecté avec succès via JNDI: {}", jndiName);

        } catch (NamingException e) {
            logger.error("Erreur lors de la connexion à l'EJB Loan", e);
            throw new RuntimeException("Impossible de se connecter à Loan EJB", e);
        }
    }

    public List<PretInfo> getPretsByCompte(Integer idCompte) {
        if (pretEJB == null) {
            logger.error("EJB Loan non initialisé");
            return List.of();
        }

        try {
            List<Pret> prets = pretEJB.getPretsByCompte(idCompte);
            List<PretInfo> result = new ArrayList<>();

            for (Pret pret : prets) {
                result.add(convertToPretInfo(pret));
            }

            logger.debug("Récupéré {} prêts pour le compte {} via EJB", result.size(), idCompte);
            return result;

        } catch (Exception e) {
            logger.error("Erreur lors de l'appel EJB getPretsByCompte pour le compte {}", idCompte, e);
            return List.of();
        }
    }

    public Double calculerMensualite(Double montant, Double taux, Integer dureeMois) {
        if (pretEJB == null) {
            logger.error("EJB Loan non initialisé");
            return 0.0;
        }

        try {
            return pretEJB.calculerMensualite(montant, taux, dureeMois);
        } catch (Exception e) {
            logger.error("Erreur lors du calcul de mensualité", e);
            return 0.0;
        }
    }

    private PretInfo convertToPretInfo(Pret pret) {
        if (pret == null) return null;

        PretInfo pretInfo = new PretInfo();
        pretInfo.setIdPret(pret.getIdPret());
        pretInfo.setMontantPret(pret.getMontantPret());
        pretInfo.setTypePret(pret.getTypePret());
        pretInfo.setIdCompteCourant(pret.getIdCompteCourant());

        return pretInfo;
    }
}