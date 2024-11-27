package com.sooraj.accountservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty(message = "firstName cannot be empty")String firstName,
        @NotEmpty(message = "lastName cannot be empty")String lastName,
        @NotEmpty(message = "email cannot be empty")String email,
        @NotEmpty(message = "password cannot be empty")String password) {
}
