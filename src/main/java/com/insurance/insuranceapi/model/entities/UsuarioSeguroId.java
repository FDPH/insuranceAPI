package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class UsuarioSeguroId {
    @Column(name = "insuredUser_id_insured_user", insertable=false, updatable=false)
    private int insuredUserId;

    @Column(name = "insuranceType_id_insurance_type", insertable=false, updatable=false)
    private int insuranceTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioSeguroId that = (UsuarioSeguroId) o;
        return insuredUserId == that.insuredUserId && insuranceTypeId == that.insuranceTypeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(insuredUserId, insuranceTypeId);
    }
}
