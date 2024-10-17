package com.insurance.insuranceapi.repository;

import com.insurance.insuranceapi.model.entities.UsuarioSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioSeguroRepository extends JpaRepository<UsuarioSeguro, Long> {

}
