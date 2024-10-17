package com.insurance.insuranceapi.repository;

import com.insurance.insuranceapi.model.entities.DocumentTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypesRepository extends JpaRepository<DocumentTypes, Integer> {
}
