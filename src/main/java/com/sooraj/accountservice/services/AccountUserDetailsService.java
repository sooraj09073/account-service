package com.sooraj.accountservice.services;

import com.sooraj.accountservice.models.UserAuthentication;
import com.sooraj.accountservice.models.UserPrincipal;
import com.sooraj.accountservice.repo.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserAuthentication user1  = authenticationRepository.findByUserId(userId);
        if(user1 == null) {
            throw new UsernameNotFoundException(userId);
        }

        return new UserPrincipal(user1);
    }
}
