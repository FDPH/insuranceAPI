package com.insurance.insuranceapi.repository;

import com.insurance.insuranceapi.model.entities.InsuredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InsuredUserRepository extends JpaRepository<InsuredUser, Integer> {

    @Query("SELECT c FROM InsuredUser c WHERE c.document_number = :document_number")
    Optional<InsuredUser> findInsuredUserByDocument_number(String document_number);

    @Query("SELECT iu FROM InsuredUser iu INNER JOIN iu.documentTypes dt WHERE iu.document_number = :document_number")
    Optional<InsuredUser> findInsuredUserByDocument_numberAndJoinType(String document_number);
}
