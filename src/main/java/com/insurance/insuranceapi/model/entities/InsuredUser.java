package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "insuredUser")
public class InsuredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_insured_user")
    private int id_insured_user;

    @Column(name = "document_number", nullable = false)
    private String document_number;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate date_of_birth;

    @ManyToOne
    @JoinColumn(name = "gender_id_gender", nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "documentTypes_id_document", nullable = false)
    private DocumentTypes documentTypes;

    public int getId_insured_user() {
        return id_insured_user;
    }

    public void setId_insured_user(int id_insured_user) {
        this.id_insured_user = id_insured_user;
    }

    public String getDocument_number() {
        return document_number;
    }

    public void setDocument_number(String document_number) {
        this.document_number = document_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public DocumentTypes getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(DocumentTypes documentTypes) {
        this.documentTypes = documentTypes;
    }
}
