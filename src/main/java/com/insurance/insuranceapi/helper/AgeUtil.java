package com.insurance.insuranceapi.helper;

import java.time.LocalDate;

public class AgeUtil {
    public static int userAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return today.getYear() - dateOfBirth.getYear();
    }
}
