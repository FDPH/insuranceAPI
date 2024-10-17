package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "insuranceDetailRange")
public class InsuranceDetailRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insurance")
    private int id_insurance;

    @Column(name = "min_age", nullable = false)
    private int min_age;

    @Column(name = "max_age", nullable = false)
    private int max_age;

    @Column(name = "prima_porcentage", nullable = false, precision = 6, scale = 5)
    private BigDecimal prima_porcentage;

    @ManyToOne
    @JoinColumn(name = "insuranceType_id_insurance_type", nullable = false)
    private InsuranceType insuranceType;

    public int getId_insurance() {
        return id_insurance;
    }

    public void setId_insurance(int id_insurance) {
        this.id_insurance = id_insurance;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }

    public BigDecimal getPrima_porcentage() {
        return prima_porcentage;
    }

    public void setPrima_porcentage(BigDecimal prima_porcentage) {
        this.prima_porcentage = prima_porcentage;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }
}
