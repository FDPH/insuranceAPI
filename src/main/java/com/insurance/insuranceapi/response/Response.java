package com.insurance.insuranceapi.response;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public static Map<String, Object> errorResponseBody(String errorMessage) {
        Map<String, Object> error = new HashMap<>();
        error.put(ResponseConstants.ERROR_MESSAGE_KEY, errorMessage);
        return error;
    }
}
