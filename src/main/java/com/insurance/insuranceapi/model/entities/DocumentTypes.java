package com.insurance.insuranceapi.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "documentTypes")
public class DocumentTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_document")
    private int id_document;


    @Column(name = "document_name", nullable = false)
    private String document_name;

    public int getId_document() {
        return id_document;
    }

    public void setId_document(int id_document) {
        this.id_document = id_document;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }
}
