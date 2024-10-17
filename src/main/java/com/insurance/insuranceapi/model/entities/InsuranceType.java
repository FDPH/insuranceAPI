package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "insuranceType")
public class InsuranceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_insurance_type")
    private int id_insurance_type;

    @Column(name = "insurance_name", length = 45)
    private String insurance_name;

    public int getId_insurance_type() {
        return id_insurance_type;
    }

    public void setId_insurance_type(int id_insurance_type) {
        this.id_insurance_type = id_insurance_type;
    }

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }
}
