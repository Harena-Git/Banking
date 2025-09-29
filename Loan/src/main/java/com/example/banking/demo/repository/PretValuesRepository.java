package com.example.banking.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.banking.demo.entity.PretValues;

@Repository
public interface PretValuesRepository extends JpaRepository<PretValues, Integer> {
}