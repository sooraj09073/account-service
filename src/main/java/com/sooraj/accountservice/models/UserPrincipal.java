package com.sooraj.accountservice.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private UserAuthentication userAuthentication;

    public UserPrincipal(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthentication.getRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRoleName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return userAuthentication.getPassword();
    }

    @Override
    public String getUsername() {
        return userAuthentication.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
