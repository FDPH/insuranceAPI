package com.insurance.insuranceapi.response;

import com.insurance.insuranceapi.model.entities.InsuranceDetailRange;
import com.insurance.insuranceapi.model.entities.InsuranceType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiquidacionResponseModel {

    public Map<String, Object> createBody(List<InsuranceDetailRange> insuranceDetailRanges, Integer valorAsegurado, String tipoIdentificacion, String nroIdentificacion) {
        List<Map<String, Object>> liquidaciones = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (InsuranceDetailRange range : insuranceDetailRanges) {
            InsuranceType insuranceType = range.getInsuranceType();

            BigDecimal primaPorcentaje = range.getPrima_porcentage();
            BigDecimal valorPoliza = BigDecimal.valueOf(valorAsegurado).multiply(primaPorcentaje);

            valorTotal = valorTotal.add(valorPoliza);

            Map<String, Object> liquidacion = new HashMap<>();
            liquidacion.put("código_amparo", insuranceType.getId_insurance_type());
            liquidacion.put("nombre_amparo", insuranceType.getInsurance_name());
            liquidacion.put("valor_prima", valorPoliza);

            liquidaciones.add(liquidacion);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tipo_identificacion", tipoIdentificacion);
        response.put("nro_identificacion", nroIdentificacion);
        response.put("valor_asegurado", valorAsegurado);
        response.put("liquidación", liquidaciones);
        response.put("Valor_total_primas", valorTotal);
        response.put("Valor_total", valorTotal.add(BigDecimal.valueOf(valorAsegurado)));

        return response;
    }
}
