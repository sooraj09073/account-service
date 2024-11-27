package com.sooraj.accountservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record SignInRequest(@NotEmpty(message = "username cannot be empty")String username,
                            @NotEmpty(message = "password cannot be empty")String password) {
}
