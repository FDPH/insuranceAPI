package com.insurance.insuranceapi.services;

import com.insurance.insuranceapi.model.dtos.LiquidacionRequestModel;
import com.insurance.insuranceapi.model.entities.DocumentTypes;
import com.insurance.insuranceapi.model.entities.InsuranceDetailRange;
import com.insurance.insuranceapi.model.entities.InsuranceType;
import com.insurance.insuranceapi.model.entities.InsuredUser;
import com.insurance.insuranceapi.repository.InsuranceDetailRangeRepository;
import com.insurance.insuranceapi.repository.InsuredUserRepository;
import com.insurance.insuranceapi.response.ResponseConstants;
import com.insurance.insuranceapi.service.LiquidacionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.insurance.insuranceapi.helper.AgeUtil.userAge;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LiquidacionTest {

    @Mock
    private InsuredUserRepository insuredUserRepository;
    @Mock
    private InsuranceDetailRangeRepository insuranceDetailRangeRepository;
    LiquidacionRequestModel request;

    @InjectMocks
    LiquidacionService liquidacionService;

    InsuredUser mockInsuredUser;
    DocumentTypes mockDocumentTypes;
    InsuranceDetailRange mockInsuranceDetailRange;
    InsuranceType mockInsuranceType;

    public void setUpGeneralData(String documentNo, String documentType) {
        mockInsuredUser = new InsuredUser();
        mockDocumentTypes = new DocumentTypes();
        mockInsuredUser.setDocumentTypes(mockDocumentTypes);
        mockInsuredUser.setDocument_number(documentNo);
        mockDocumentTypes.setDocument_name(documentType);

        when(insuredUserRepository.findInsuredUserByDocument_numberAndJoinType(anyString()))
                .thenReturn(Optional.of(mockInsuredUser));

    }

    public void setUpGeneralData(String documentNo, String documentType, String age){
        setUpGeneralData(documentNo, documentType);
        mockInsuredUser.setDate_of_birth(LocalDate.parse(age));
    }


    private void setUpInsurance(String documentNo, String documentType, String age) {
        setUpGeneralData(documentNo, documentType, age);
        mockInsuranceDetailRange = new InsuranceDetailRange();
        mockInsuranceType = new InsuranceType();
        mockInsuranceDetailRange.setInsuranceType(mockInsuranceType);
        mockInsuranceDetailRange.setId_insurance(1);

        mockInsuranceDetailRange.setPrima_porcentage(BigDecimal.valueOf(0.18090));
        mockInsuranceType.setInsurance_name("Muerte accidental");
        mockInsuranceType.setId_insurance_type(1);

        when(insuranceDetailRangeRepository.findByAgeAndJoinInsuranceType(userAge(mockInsuredUser.getDate_of_birth())))
                .thenReturn(List.of(mockInsuranceDetailRange));
    }


    @DisplayName("Usuario no existe")
    @Test
    public void nroDocumentNotExist() {
        request = new LiquidacionRequestModel("cc", "1523456", 10);

        ResponseEntity<List<Map<String, Object>>> listResponseEntity = liquidacionService.procesarLiquidacion(request);

        String errorMessage = listResponseEntity.getBody().stream()
                .filter(map -> map.containsKey(ResponseConstants.ERROR_MESSAGE_KEY))
                .map(map -> (String) map.get(ResponseConstants.ERROR_MESSAGE_KEY))
                .findFirst()
                .orElse("No error message found");

        Assertions.assertEquals(ResponseConstants.NO_SE_ENCUENTRAN_RESULTADOS_EN_INSURED_USER, errorMessage);
    }

    @DisplayName("El tipo de dato no se encuentra")
    @Test
    public void documentTypeIsNotCorrect() {
        setUpGeneralData("cc", "79000001");
        request = new LiquidacionRequestModel("Cedula", "79000001", 10);

        ResponseEntity<List<Map<String, Object>>> listResponseEntity = liquidacionService.procesarLiquidacion(request);

        String errorMessage = listResponseEntity.getBody().stream()
                .filter(map -> map.containsKey(ResponseConstants.ERROR_MESSAGE_KEY))
                .map(map -> (String) map.get(ResponseConstants.ERROR_MESSAGE_KEY))
                .findFirst()
                .orElse("No error message found");

        Assertions.assertEquals(ResponseConstants.EL_TIPO_DE_IDENTIFICACION_NO_COINCIDE_CON_EL_ALMACENADO_EN_BASE_DE_DATOS, errorMessage);
    }

    @DisplayName("Ningun insurance aplica debido a la edad")
    @Test
    public void notPossibleApplyInsurance() {
        setUpGeneralData("79000001", "cc", "1945-01-10");

        request = new LiquidacionRequestModel("cc", "79000001", 10);

        ResponseEntity<List<Map<String, Object>>> listResponseEntity = liquidacionService.procesarLiquidacion(request);

        String errorMessage = listResponseEntity.getBody().stream()
                .filter(map -> map.containsKey(ResponseConstants.ERROR_MESSAGE_KEY))
                .map(map -> (String) map.get(ResponseConstants.ERROR_MESSAGE_KEY))
                .findFirst()
                .orElse("No error message found");

        Assertions.assertEquals(ResponseConstants.INSURANCE_NOT_AVAILABLE_FOR_USER, errorMessage);
    }

    @DisplayName("One Insurance Applied")
    @Test
    public void oneInsuranceAvailable() {
        setUpInsurance("79000002", "cc", "1950-01-10");
        mockInsuranceDetailRange.setMin_age(0);
        mockInsuranceDetailRange.setMax_age(100);
        request = new LiquidacionRequestModel("cc", "79000002", 1000);

        ResponseEntity<List<Map<String, Object>>> listResponseEntity = liquidacionService.procesarLiquidacion(request);

        String messageFound = listResponseEntity.getStatusCode().toString();

        Assertions.assertEquals("200 OK", messageFound);
    }

}
