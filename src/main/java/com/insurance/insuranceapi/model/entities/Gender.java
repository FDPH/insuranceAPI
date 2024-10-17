package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "gender")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_gender")
    private int id_gender;

    @Column(name = "gender_type" , length = 45)
    private String gender_type;

    public int getId_gender() {
        return id_gender;
    }

    public void setId_gender(int id_gender) {
        this.id_gender = id_gender;
    }

    public String getGender_type() {
        return gender_type;
    }

    public void setGender_type(String gender_type) {
        this.gender_type = gender_type;
    }
}
