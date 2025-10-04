package com.example.banking.loan.ejb;

import com.example.banking.loan.model.Pret;
import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface PretEJBRemote {
    List<Pret> getAllPrets();
    List<Pret> getPretsByCompte(Integer idCompte);
    Pret createPret(Pret pret);
    Pret getPretById(Integer id);
}