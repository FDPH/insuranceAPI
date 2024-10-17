package com.insurance.insuranceapi.repository;

import com.insurance.insuranceapi.model.entities.InsuranceDetailRange;
import com.insurance.insuranceapi.model.entities.InsuredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceDetailRangeRepository extends JpaRepository<InsuranceDetailRange,Integer> {
    @Query("SELECT r FROM InsuranceDetailRange r WHERE :age BETWEEN r.min_age AND r.max_age")
    List<InsuranceDetailRange> findByAgeInRange(int age);

    @Query("SELECT r FROM InsuranceDetailRange r INNER JOIN r.insuranceType it WHERE r.min_age <= :age AND r.max_age >= :age")
    List<InsuranceDetailRange> findByAgeAndJoinInsuranceType(int age);
}
