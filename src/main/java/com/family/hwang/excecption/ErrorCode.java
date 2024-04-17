package com.family.hwang.excecption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //CONFLICT
    ALREADY_LIKED_POST(HttpStatus.CONFLICT, "user already like the post"),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated user name"),

    //INTERNAL_SERVER_ERROR
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    //UNAUTHORIZED
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),

    //NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded");

    private final HttpStatus status;
    private final String message;
}
