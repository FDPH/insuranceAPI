package com.insurance.insuranceapi.service;

import com.insurance.insuranceapi.model.dtos.LiquidacionRequestModel;
import com.insurance.insuranceapi.model.entities.DocumentTypes;
import com.insurance.insuranceapi.model.entities.InsuranceDetailRange;
import com.insurance.insuranceapi.model.entities.InsuredUser;
import com.insurance.insuranceapi.repository.InsuranceDetailRangeRepository;
import com.insurance.insuranceapi.repository.InsuredUserRepository;
import com.insurance.insuranceapi.response.LiquidacionResponseModel;
import com.insurance.insuranceapi.response.ResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.insurance.insuranceapi.helper.AgeUtil.userAge;
import static com.insurance.insuranceapi.helper.Response.errorResponseBody;

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
            responseBodyList.add(errorResponseBody(ResponseConstants.INSURANCE_NOT_AVAILABLE_FOR_USER));
            return ResponseEntity.badRequest().body(responseBodyList);
        }

        List<InsuranceDetailRange> insuranceDetailRange = insuranceDetailRanges;
        Map<String, Object> body = builder.createBody(insuranceDetailRange, valorAsegurado, user.getDocument_number(), documentType.getDocument_name());
        responseBodyList.add(body);

        return ResponseEntity.ok().body(responseBodyList);
    }

    public ResponseEntity<List<Map<String, Object>>> checkIfIdentificationTypeIsCorrect(DocumentTypes documentType, LiquidacionRequestModel solicitud, List<Map<String, Object>> responseBodyList) {
        if (!documentType.getDocument_name().equalsIgnoreCase(solicitud.getTipoIdentificacion())) {
            responseBodyList.add(errorResponseBody(ResponseConstants.EL_TIPO_DE_IDENTIFICACION_NO_COINCIDE_CON_EL_ALMACENADO_EN_BASE_DE_DATOS));
            return ResponseEntity.badRequest().body(responseBodyList);
        }
        return null;
    }

    public ResponseEntity<List<Map<String, Object>>> checkIfInsuredUserExist(Optional<InsuredUser> insuredUser, List<Map<String, Object>> responseBodyList) {
        if (!insuredUser.isPresent()) {
            responseBodyList.add(errorResponseBody(ResponseConstants.NO_SE_ENCUENTRAN_RESULTADOS_EN_INSURED_USER));
            return ResponseEntity.badRequest().body(responseBodyList);
        }
        return null;
    }


}