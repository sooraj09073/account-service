package com.sooraj.accountservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserProfileDTO(
        @NotEmpty(message = "firstName cannot be empty") String firstName,
        @NotEmpty(message = "lastName cannot be empty") String lastName,
        @NotEmpty(message = "emailId cannot be empty") String emailId,
         String phoneNumber,
         UserAddressDTO address
) {
}
