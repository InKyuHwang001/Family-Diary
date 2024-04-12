package com.family.hwang.excecption;


import lombok.Getter;

@Getter
public class HwangFamilyException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public HwangFamilyException(ErrorCode errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public HwangFamilyException(ErrorCode errorCode) {
        this.message = null;
        this.errorCode = errorCode;
    }


    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        } else {
            return String.format("%s. %s", errorCode.getMessage(), message);
        }

    }
}
