package com.sooraj.accountservice.controllers;

import com.sooraj.accountservice.dto.JwtToken;
import com.sooraj.accountservice.dto.SignInRequest;
import com.sooraj.accountservice.services.UserAuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSignIn {

    @Autowired
    UserAuthenticateService userAuthenticateService;

    @PostMapping("/sign-in")
    public JwtToken authenticate(@RequestBody SignInRequest signInRequest) {
        return userAuthenticateService.verify(signInRequest);
    }
}
