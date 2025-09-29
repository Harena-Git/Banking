package com.example.banking.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.banking.demo.entity.Pret;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
}