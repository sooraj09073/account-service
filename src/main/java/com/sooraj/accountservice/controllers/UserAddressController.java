package com.sooraj.accountservice.controllers;

import com.sooraj.accountservice.dto.UserAddressDTO;
import com.sooraj.accountservice.models.UserPrincipal;
import com.sooraj.accountservice.services.UserAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserAddressController {

    @Autowired
    UserAddressService userAddressService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/address")
    public UserAddressDTO addUserAddress(@Valid @RequestBody UserAddressDTO UserAddressDTO) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = (String) auth.getPrincipal();
        return userAddressService.postUserAddress(UserAddressDTO, user);
    }
}
