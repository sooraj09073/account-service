package com.sooraj.accountservice.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserAddressDTO(
        @NotEmpty(message = "AddressLine 1 cannot be empty") String addressLine1,
        String addressLine2,
        @NotEmpty(message = "city cannot be empty")  String city,
        @NotEmpty(message = "state cannot be empty") String state,
        @NotEmpty(message = "country cannot be empty") String country,
        @NotEmpty(message = "postal code cannot be empty") String postalCode
) {
}
