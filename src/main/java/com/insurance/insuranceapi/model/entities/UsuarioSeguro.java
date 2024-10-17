package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "insuredUser_has_insuranceType")
public class UsuarioSeguro {
    @EmbeddedId
    private UsuarioSeguroId id;

    @Column(name = "init_date")
    private LocalDate init_date;

    @Column(name = "end_date")
    private LocalDate end_date;

    @ManyToOne
    @JoinColumn(name = "insuredUser_id_insured_user", nullable = false)
    private InsuredUser insuredUser;

    @ManyToOne
    @JoinColumn(name = "insuranceType_id_insurance_type", nullable = false)
    private InsuranceType insuranceType;


}
