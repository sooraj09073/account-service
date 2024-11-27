package com.sooraj.accountservice.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomExceptionController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        errorDetails.put("status", statusCode);
        errorDetails.put("message", "Custom error handling");

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(statusCode));
    }

}
