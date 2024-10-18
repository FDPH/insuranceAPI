package com.insurance.insuranceapi.service;

import com.insurance.insuranceapi.model.dtos.LiquidacionRequestModel;
import com.insurance.insuranceapi.model.entities.DocumentTypes;
import com.insurance.insuranceapi.model.entities.InsuranceDetailRange;
import com.insurance.insuranceapi.model.entities.InsuredUser;
import com.insurance.insuranceapi.repository.InsuranceDetailRangeRepository;
import com.insurance.insuranceapi.repository.InsuredUserRepository;
import com.insurance.insuranceapi.response.LiquidacionResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.insurance.insuranceapi.response.Response.errorResponseBody;

@Service
public class LiquidacionService {

    private static final Logger logger = LoggerFactory.getLogger(LiquidacionService.class);

    private final InsuredUserRepository insuredUserRepository;
    private final InsuranceDetailRangeRepository insuranceDetailRangeRepository;

    public LiquidacionService(InsuredUserRepository insuredUserRepository, InsuranceDetailRangeRepository insuranceDetailRangeRepository) {
        this.insuredUserRepository = insuredUserRepository;
        this.insuranceDetailRangeRepository = insuranceDetailRangeRepository;
    }

    public ResponseEntity<List<Map<String, Object>>> procesarLiquidacion(LiquidacionRequestModel bodyRequest) {

        List<Map<String, Object>> responseBodyList = new ArrayList<>();
        Integer valorAsegurado = bodyRequest.getValorAsegurado();
        LiquidacionResponseModel builder = new LiquidacionResponseModel();

        Optional<InsuredUser> insuredUser = insuredUserRepository.findInsuredUserByDocument_numberAndJoinType(bodyRequest.getNroIdentificacion());

        ResponseEntity<List<Map<String, Object>>> insuredUserBodyResponse = checkIfInsuredUserExist(insuredUser, responseBodyList);
        if (insuredUserBodyResponse != null) return insuredUserBodyResponse;

        InsuredUser user = insuredUser.get();
        DocumentTypes documentType = user.getDocumentTypes();

        ResponseEntity<List<Map<String, Object>>> identificationBodyResponse = checkIfIdentificationTypeIsCorrect(documentType, bodyRequest, responseBodyList);
        if (identificationBodyResponse != null) return identificationBodyResponse;

        int userAge = userAge(user.getDate_of_birth());

        List<InsuranceDetailRange> insuranceDetailRanges = insuranceDetailRangeRepository.findByAgeAndJoinInsuranceType(userAge);
        if (insuranceDetailRanges.isEmpty()) {
            responseBodyList.add(errorResponseBody("No se encuentran resultados en insuranceDetailRanges"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }

        List<InsuranceDetailRange> insuranceDetailRange = insuranceDetailRanges;

        Map<String, Object> body = builder.createBody(insuranceDetailRange, valorAsegurado, user.getDocument_number(), documentType.getDocument_name());

        responseBodyList.add(body);

        return ResponseEntity.ok().body(responseBodyList);

    }

    private ResponseEntity<List<Map<String, Object>>> checkIfIdentificationTypeIsCorrect(DocumentTypes documentType, LiquidacionRequestModel solicitud, List<Map<String, Object>> responseBodyList) {
        if (!documentType.getDocument_name().equalsIgnoreCase(solicitud.getTipoIdentificacion())) {
            responseBodyList.add(errorResponseBody("El tipo de identificacion no coincide con el almacenado en base de datos"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }
        return null;
    }

    private ResponseEntity<List<Map<String, Object>>> checkIfInsuredUserExist(Optional<InsuredUser> insuredUser, List<Map<String, Object>> responseBodyList) {
        if (!insuredUser.isPresent()) {
            responseBodyList.add(errorResponseBody("No se encuentran resultados en insuredUser"));
            return ResponseEntity.badRequest().body(responseBodyList);
        }
        return null;
    }

    private int userAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return today.getYear() - dateOfBirth.getYear();
    }
}