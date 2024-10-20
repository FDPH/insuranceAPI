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
            liquidacion.put(ResponseConstants.CODIGO_AMPARO, insuranceType.getId_insurance_type());
            liquidacion.put(ResponseConstants.NOMBRE_AMPARO, insuranceType.getInsurance_name());
            liquidacion.put(ResponseConstants.VALOR_PRIMA, valorPoliza);

            liquidaciones.add(liquidacion);
        }

        Map<String, Object> response = new HashMap<>();
        response.put(ResponseConstants.TIPO_IDENTIFICACION, tipoIdentificacion);
        response.put(ResponseConstants.NRO_IDENTIFICACION, nroIdentificacion);
        response.put(ResponseConstants.VALOR_ASEGURADO, valorAsegurado);
        response.put(ResponseConstants.LIQUIDACION, liquidaciones);
        response.put(ResponseConstants.VALOR_TOTAL_PRIMAS, valorTotal);
        response.put(ResponseConstants.VALOR_TOTAL, valorTotal.add(BigDecimal.valueOf(valorAsegurado)));

        return response;
    }
}
