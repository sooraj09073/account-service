package com.sooraj.accountservice.services;

import com.sooraj.accountservice.dto.JwtToken;
import com.sooraj.accountservice.dto.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticateService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public JwtToken verify(SignInRequest signInRequest) {
       Authentication authentication =  authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       signInRequest.username(), signInRequest.password()));

       if (authentication.isAuthenticated()) {
           return jwtService.generateToken(signInRequest.username());
       }
       return new JwtToken("Error");
    }
}