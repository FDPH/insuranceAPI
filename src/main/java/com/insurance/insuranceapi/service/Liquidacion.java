package com.insurance.insuranceapi.service;

import java.math.BigDecimal;

public class Liquidacion {
    private String codigoAmparo;
    private String nombreAmparo;
    private BigDecimal valorPrima;

    public Liquidacion(String codigoAmparo, String nombreAmparo, BigDecimal valorPrima) {
        this.codigoAmparo = codigoAmparo;
        this.nombreAmparo = nombreAmparo;
        this.valorPrima = valorPrima;
    }

    public String getCodigoAmparo() {
        return codigoAmparo;
    }

    public void setCodigoAmparo(String codigoAmparo) {
        this.codigoAmparo = codigoAmparo;
    }

    public String getNombreAmparo() {
        return nombreAmparo;
    }

    public void setNombreAmparo(String nombreAmparo) {
        this.nombreAmparo = nombreAmparo;
    }

    public BigDecimal getValorPrima() {
        return valorPrima;
    }

    public void setValorPrima(BigDecimal valorPrima) {
        this.valorPrima = valorPrima;
    }
}
