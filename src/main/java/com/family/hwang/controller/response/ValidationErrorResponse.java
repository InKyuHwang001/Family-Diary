package com.family.hwang.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter

public class ValidationErrorResponse {

    private  Map<String, String> validation;

    @Builder
    public ValidationErrorResponse(Map<String, String> validation) {
        this.validation = validation;
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
