package com.insurance.insuranceapi.repository;

import com.insurance.insuranceapi.model.entities.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Integer> {
}
