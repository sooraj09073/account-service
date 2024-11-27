package com.sooraj.accountservice.configs;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sooraj.accountservice.dto.ErrorMessageDTO;
import com.sooraj.accountservice.exceptions.NoDataChangeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        Throwable cause = e.getCause();
        Map<String, String> reason = new HashMap<>();
        reason.put("message", "Invalid request payload");
        if (cause instanceof UnrecognizedPropertyException unrecognizedPropertyException) {
            String fieldName = unrecognizedPropertyException.getPropertyName();
            reason.put("error", "Unrecognized field");
            reason.put("field", fieldName);
            reason.put("message", "Unrecognized field: '" + fieldName + "'. Please check your request.");
        }else if (cause instanceof InvalidFormatException invalidFormatException) {
            reason.put("error", "Invalid value for field");
            reason.put("field", invalidFormatException.getPathReference());
            reason.put("message", "Invalid value for field: " + invalidFormatException.getPathReference());
        } else if (cause instanceof JsonParseException jsonParseException) {
            reason.put("error", "Malformed JSON request");
            reason.put("message", jsonParseException.getOriginalMessage());
        }else {
            reason.put("error", "General error");
            reason.put("message", "An unexpected error occurred");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getClass().getSimpleName(),
                        e.getMessage().split(":")[0],
                        reason
                ).toString());
    }

    @ExceptionHandler(value = { AuthenticationCredentialsNotFoundException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        e.getClass().getSimpleName(),
                        "Authorization token provided is not valid",
                        Map.of("message",e.getLocalizedMessage())
                ).toString());
    }

    @ExceptionHandler(value = { ExpiredJwtException.class, MalformedJwtException.class })
    public ResponseEntity<String> handleJwtException(JwtException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        e.getClass().getSimpleName(),
                        "Authorization token provided is not valid",
                        Map.of("message",e.getLocalizedMessage().split(":")[0])
                ).toString());
    }

    @ExceptionHandler(value = { NoResourceFoundException.class })
    public ResponseEntity<String> handleExpiredJwt(NoResourceFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        e.getClass().getSimpleName(),
                        e.getMessage(),
                        Map.of("message",e.getHttpMethod()+ " /" +e.getResourcePath() + " not found")
                ).toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        // Get all field errors from the exception
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // Loop through each field error and add it to the map
        for (FieldError fieldError : fieldErrors) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);  // Add field and message
        }

        // Return the response with all validation error messages
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        e.getClass().getSimpleName(),
                        e.getBody().getDetail(),
                        errors
                ).toString());
    }

    @ExceptionHandler(value = { NoDataChangeException.class })
    public ResponseEntity<String> handleException(RuntimeException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getClass().getSimpleName(),
                        "Operation cannot continue",
                        Map.of("message",e.getMessage())
                ).toString());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<String> handleException(Exception e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new ErrorMessageDTO(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getClass().getSimpleName(),
                        e.getMessage(),
                        Map.of("message","An unexpected error occurred")
                ).toString());
    }
}
