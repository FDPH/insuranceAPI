package com.insurance.insuranceapi.service;

import com.insurance.insuranceapi.model.dtos.LiquidacionRequestModel;
import com.insurance.insuranceapi.model.dtos.LiquidacionResponseModel;
import com.insurance.insuranceapi.model.dtos.ResponseConstants;
import com.insurance.insuranceapi.model.entities.DocumentTypes;
import com.insurance.insuranceapi.model.entities.InsuranceDetailRange;
import com.insurance.insuranceapi.model.entities.InsuredUser;
import com.insurance.insuranceapi.repository.InsuranceDetailRangeRepository;
import com.insurance.insuranceapi.repository.InsuredUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LiquidacionService {

    private static final Logger logger = LoggerFactory.getLogger(LiquidacionService.class);

    private final InsuredUserRepository insuredUserRepository;
    private final InsuranceDetailRangeRepository insuranceDetailRangeRepository;

    public LiquidacionService(InsuredUserRepository insuredUserRepository, InsuranceDetailRangeRepository insuranceDetailRangeRepository) {
        this.insuredUserRepository = insuredUserRepository;
        this.insuranceDetailRangeRepository = insuranceDetailRangeRepository;
    }

    public ResponseEntity<List<Map<String, Object>>> procesarLiquidacion(List<LiquidacionRequestModel> requests) {

        List<Map<String, Object>> responseBodyList = new ArrayList<>();
        LiquidacionRequestModel solicitud = requests.get(0);
        Integer valorAsegurado = solicitud.getValorAsegurado();
        LiquidacionResponseModel builder = new LiquidacionResponseModel();

        if (solicitud.getNroIdentificacion() == null || solicitud.getTipoIdentificacion() == null || solicitud.getValorAsegurado() == null) {
            logger.info("Datos suministrados: No identificacion {} Tipo identificacion {} Valor asegurado {}",solicitud.getNroIdentificacion(), solicitud.getTipoIdentificacion(), solicitud.getValorAsegurado());
            responseBodyList.add(errorResponseBody ( "Validar el request, faltan datos obligatorios"));
            return ResponseEntity.status(500).body(responseBodyList);
        }
        if (solicitud.getValorAsegurado() <= 0) {
            logger.info("Valor asegurado {}", solicitud.getValorAsegurado());
            responseBodyList.add(errorResponseBody(" El valor asegurado no puede ser igual a 0"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }

        Optional<InsuredUser> insuredUser = insuredUserRepository.findInsuredUserByDocument_numberAndJoinType(solicitud.getNroIdentificacion());

        if (!insuredUser.isPresent()) {
            responseBodyList.add(errorResponseBody ("No se encuentran resultados en insuredUser"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }

        InsuredUser user = insuredUser.get();
        DocumentTypes documentType = user.getDocumentTypes();

        if (!documentType.getDocument_name().equalsIgnoreCase(solicitud.getTipoIdentificacion())) {
            responseBodyList.add(errorResponseBody ( "El tipo de identificacion no coincide con el almacenado en base de datos"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }

        int userAge = userAge(user.getDate_of_birth());

        List<InsuranceDetailRange> insuranceDetailRanges = insuranceDetailRangeRepository.findByAgeAndJoinInsuranceType(userAge);
        if (insuranceDetailRanges.isEmpty()) {
            responseBodyList.add(errorResponseBody ( "No se encuentran resultados en insuranceDetailRanges"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }

        List<InsuranceDetailRange> insuranceDetailRange = insuranceDetailRanges;

        Map<String, Object> body = builder.createBody(insuranceDetailRange, valorAsegurado, user.getDocument_number(), documentType.getDocument_name());

        responseBodyList.add(body);

        return ResponseEntity.ok().body(responseBodyList);

    }


    private Map<String, Object>  errorResponseBody(String errorMessage) {
        Map<String, Object> error = new HashMap<>();
        error.put(ResponseConstants.ERROR_MESSAGE_KEY, errorMessage);
       return error;
    }

    private int userAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return today.getYear() - dateOfBirth.getYear();
    }

}