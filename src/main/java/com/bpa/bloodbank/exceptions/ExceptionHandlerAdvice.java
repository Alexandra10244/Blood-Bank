package com.bpa.bloodbank.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(DonorNotFoundException.class)
    public ResponseEntity<String> donorNotFoundException(DonorNotFoundException donorNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", donorNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(DonationNotFoundException.class)
    public ResponseEntity<String> donationNotFoundException(DonationNotFoundException donationNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", donationNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(CenterNotFoundException.class)
    public ResponseEntity<String> centerNotFoundException(CenterNotFoundException centerNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", centerNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> invalidDateException(InvalidDateException invalidDateException) {
        return new ResponseEntity<>(objectToString(Map.of("message", invalidDateException.getMessage())), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            String message = violation.getMessage();
            errors.put(fieldName, message);
        }
        return new ResponseEntity<>(objectToString(errors), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String defaultMessage = Objects.requireNonNull(error.getDefaultMessage());
            errors.put(error.getField(), defaultMessage);
        });
        return new ResponseEntity<>(objectToString(errors), HttpStatus.BAD_REQUEST);
    }

    private String objectToString(Object response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            logger.error("Error processing response to string");
            return "Internal error";
        }
    }
}