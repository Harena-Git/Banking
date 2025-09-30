package com.example.banking.demo.repository;

import com.example.banking.demo.entity.PretValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PretValuesRepository extends JpaRepository<PretValues, Integer> {
}