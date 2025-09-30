package com.example.banking.demo.repository;

import com.example.banking.demo.entity.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
}