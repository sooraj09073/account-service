package com.sooraj.accountservice.controllers;

import com.sooraj.accountservice.dto.RegisterRequest;
import com.sooraj.accountservice.dto.UserProfileDTO;
import com.sooraj.accountservice.services.UserProfileService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRegister {

    @Autowired
    UserProfileService userProfileService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequest registerRequest) {
        userProfileService.registerUser(registerRequest);
    }
    @GetMapping("/profile")
    public UserProfileDTO getProfile(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = (String) auth.getPrincipal();
        return userProfileService.getProfile(user);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/update")
    public UserProfileDTO updateProfile(@RequestBody UserProfileDTO userProfileDTO) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = (String) auth.getPrincipal();
        return userProfileService.update(userProfileDTO, user);
    }
}
