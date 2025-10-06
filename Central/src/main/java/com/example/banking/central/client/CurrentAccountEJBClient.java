package com.example.banking.central.client;

import com.example.banking.currentaccount.ejb.CompteCourantEJBRemote;
import com.example.banking.currentaccount.model.CompteCourant;
import com.example.banking.central.model.CompteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.naming.Context;
import jakarta.naming.InitialContext;
import jakarta.naming.NamingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CurrentAccountEJBClient {
    private static final Logger logger = LoggerFactory.getLogger(CurrentAccountEJBClient.class);
    private CompteCourantEJBRemote compteEJB;
    private final Properties config;

    public CurrentAccountEJBClient(Properties config) {
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

            // JNDI Lookup pour l'EJB remote de CurrentAccount
            String jndiName = "ejb:/currentaccount/CompteCourantEJB!com.example.banking.currentaccount.ejb.CompteCourantEJBRemote";
            compteEJB = (CompteCourantEJBRemote) context.lookup(jndiName);

            logger.info("EJB CurrentAccount connecté avec succès via JNDI: {}", jndiName);

        } catch (NamingException e) {
            logger.error("Erreur lors de la connexion à l'EJB CurrentAccount", e);
            throw new RuntimeException("Impossible de se connecter à CurrentAccount EJB", e);
        }
    }

    public CompteInfo getCompteById(Integer idCompte) {
        if (compteEJB == null) {
            logger.error("EJB CurrentAccount non initialisé");
            return null;
        }

        try {
            CompteCourant compte = compteEJB.getCompteById(idCompte);
            return convertToCompteInfo(compte);
        } catch (Exception e) {
            logger.error("Erreur lors de l'appel EJB getCompteById pour le compte {}", idCompte, e);
            return null;
        }
    }

    public List<CompteInfo> getAllComptes() {
        if (compteEJB == null) {
            logger.error("EJB CurrentAccount non initialisé");
            return List.of();
        }

        try {
            List<CompteCourant> comptes = compteEJB.getAllComptes();
            List<CompteInfo> result = new ArrayList<>();

            for (CompteCourant compte : comptes) {
                result.add(convertToCompteInfo(compte));
            }

            logger.debug("Récupéré {} comptes via EJB", result.size());
            return result;

        } catch (Exception e) {
            logger.error("Erreur lors de l'appel EJB getAllComptes", e);
            return List.of();
        }
    }

    private CompteInfo convertToCompteInfo(CompteCourant compte) {
        if (compte == null) return null;

        return new CompteInfo(
                compte.getIdCompteCourant(),
                compte.getSoldeCompteCourant(),
                compte.getDateCreation(),
                compte.getStatut()
        );
    }
}