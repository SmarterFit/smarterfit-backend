package com.smarterfit.common.handler;

import com.smarterfit.common.dto.response.ApiError;
import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body("Validation error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> genericException(Exception ex) {
        return createResponseApiError(HttpStatus.INTERNAL_SERVER_ERROR, List.of(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFoundException(RuntimeException ex) {
        return createResponseApiError(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> emailExistsException(RuntimeException ex) {
        return createResponseApiError(HttpStatus.CONFLICT, List.of(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentException(IllegalArgumentException ex) {
        return createResponseApiError(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ApiError> handleValidationExceptions(Exception ex) {
        List<String> errorList;

        if (ex instanceof MethodArgumentNotValidException) {
            // Tratar MethodArgumentNotValidException
            MethodArgumentNotValidException mavnEx = (MethodArgumentNotValidException) ex;
            errorList = mavnEx.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
        } else if (ex instanceof HttpMessageNotReadableException) {
            // Tratar HttpMessageNotReadableException
            errorList = List.of("Malformed JSON request.");
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            // Tratar MethodArgumentTypeMismatchException
            MethodArgumentTypeMismatchException matmEx = (MethodArgumentTypeMismatchException) ex;
            errorList = List.of("Invalid argument type for parameter: " + matmEx.getName());
        } else {
            errorList = List.of("Unexpected error occurred.");
        }

        return createResponseApiError(HttpStatus.BAD_REQUEST, errorList);
    }

    private ResponseEntity<ApiError> createResponseApiError(HttpStatus status, List<String> errors) {
        ApiError apiError = ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(status.value())
                .status(status.name())
                .errors(errors)
                .build();
        return new ResponseEntity<>(apiError, status);
    }
}
