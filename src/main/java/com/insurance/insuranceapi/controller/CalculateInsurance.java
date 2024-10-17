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

import java.util.List;
import java.util.Map;

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
        return liquidacionService.procesarLiquidacion(requests);
    }

}
