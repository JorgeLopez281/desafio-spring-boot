package com.tecnova.technical_test.infrastructure.adapter.exceptions;

import org.springframework.http.HttpStatus;

public class UsersException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus errorCode;
    private String errorMessage;

    public UsersException(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public UsersException() {
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
