package com.insurance.insuranceapi.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquidacionRequestModel {

    public LiquidacionRequestModel(String tipoIdentificacion, String nroIdentificacion, Integer valorAsegurado) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.nroIdentificacion = nroIdentificacion;
        this.valorAsegurado = valorAsegurado;
    }

    @JsonProperty("tipo_identificacion")
    private String tipoIdentificacion;

    @JsonProperty("nro_identificacion")
    private String nroIdentificacion;

    @JsonProperty("valor_asegurado")
    private Integer valorAsegurado;

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public Integer getValorAsegurado() {
        return valorAsegurado;
    }

    public void setValorAsegurado(Integer valorAsegurado) {
        this.valorAsegurado = valorAsegurado;
    }
}
