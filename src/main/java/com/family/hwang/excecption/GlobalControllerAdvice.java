package com.family.hwang.excecption;

import com.family.hwang.controller.response.Response;
import com.family.hwang.controller.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> applicationHandler(MethodArgumentNotValidException e) {
        log.error("Error occurs {}", e.toString());

        ValidationErrorResponse response = ValidationErrorResponse.builder().build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(Response.error(ErrorCode.INVALID_INPUT.name(), response));
    }

    @ExceptionHandler(HwangFamilyRuntimeException.class)
    public ResponseEntity<?> applicationHandler(HwangFamilyRuntimeException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> applicationHandler(RuntimeException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(Response.error(ErrorCode.INTERNAL_SERVER_ERROR.name()));
    }

}
