package com.insurance.insuranceapi.controller;

import com.insurance.insuranceapi.api.InsuranceAPIConstans;
import com.insurance.insuranceapi.model.dtos.LiquidacionRequestModel;
import com.insurance.insuranceapi.service.LiquidacionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.insurance.insuranceapi.helper.Response.errorResponseBody;

@RestController
@RequestMapping(InsuranceAPIConstans.FEATURE_NAME)
@Api(value = "UsuarioController", description = "Operaciones relacionadas con usuarios")
public class CalculateInsurance {
    private static final Logger logger = LoggerFactory.getLogger(CalculateInsurance.class);

    private final LiquidacionService liquidacionService;

    @Autowired
    public CalculateInsurance(LiquidacionService liquidacionService) {
        this.liquidacionService = liquidacionService;
    }

    @ApiOperation(value = "Obtiene una lista de usuarios")
    @PostMapping("/calcularLiquidacion")
    public ResponseEntity<List<Map<String, Object>>> calcularLiquidacion(@RequestBody List<LiquidacionRequestModel> requests){
        logger.info("Calculando liquidacion");
        List<Map<String, Object>> responseBodyList = new ArrayList<>();
        LiquidacionRequestModel bodyRequest = requests.get(0);
        ResponseEntity<List<Map<String, Object>>> requestCheckedBodyResponse = checkIfRequestIsCorrect(bodyRequest, responseBodyList);
        if (requestCheckedBodyResponse != null) return requestCheckedBodyResponse;
        return liquidacionService.procesarLiquidacion(bodyRequest);
    }

    private ResponseEntity<List<Map<String, Object>>> checkIfRequestIsCorrect(LiquidacionRequestModel solicitud, List<Map<String, Object>> responseBodyList) {
        if (solicitud.getNroIdentificacion() == null || solicitud.getTipoIdentificacion() == null || solicitud.getValorAsegurado() == null) {
            logger.info("Datos suministrados: No identificacion {} Tipo identificacion {} Valor asegurado {}", solicitud.getNroIdentificacion(), solicitud.getTipoIdentificacion(), solicitud.getValorAsegurado());
            responseBodyList.add(errorResponseBody ( "Validar el request, faltan datos obligatorios"));
            return ResponseEntity.status(500).body(responseBodyList);
        }
        if (solicitud.getValorAsegurado() <= 0) {
            logger.info("Valor asegurado {}", solicitud.getValorAsegurado());
            responseBodyList.add(errorResponseBody(" El valor asegurado no puede ser igual a 0"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }
        return null;
    }

}
