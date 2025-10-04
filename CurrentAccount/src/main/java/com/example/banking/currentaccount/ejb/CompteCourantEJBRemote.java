package com.example.banking.currentaccount.ejb;

import com.example.banking.currentaccount.model.CompteCourant;
import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface CompteCourantEJBRemote {
    List<CompteCourant> getAllComptes();
    CompteCourant getCompteById(Integer id);
    CompteCourant createCompte(CompteCourant compte);
    void updateSolde(Integer idCompte, Double nouveauSolde);
    boolean compteExiste(Integer idCompte);
}