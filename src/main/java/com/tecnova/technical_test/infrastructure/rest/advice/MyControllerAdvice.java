package com.tecnova.technical_test.infrastructure.rest.advice;

import com.tecnova.technical_test.infrastructure.adapter.exceptions.AuthException;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.ResourceNotFoundException;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UserException;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UsersException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleEmptyInput(UserException emptyInputException){
        return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
    }

    @ExceptionHandler(UsersException.class)
    public ResponseEntity<String> handleGetUsersNoFound(UserException emptyInputException){
        return new ResponseEntity<String>(emptyInputException.getErrorMessage(), emptyInputException.getErrorCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Resource not found");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid petition");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal Server Error");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, String>> handleAuthException(AuthException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unauthorized");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
