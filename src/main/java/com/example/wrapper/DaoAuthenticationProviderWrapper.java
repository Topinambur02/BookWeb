package com.example.wrapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.experimental.Accessors;

public class DaoAuthenticationProviderWrapper extends DaoAuthenticationProvider {

    @Data
    @Accessors(fluent = true, chain = true)
    public static class Builder {
        
        private UserDetailsService userDetailsService;
        private PasswordEncoder passwordEncoder;

        public AuthenticationManager build() {
            final var provider = new DaoAuthenticationProviderWrapper();

            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder);

            return new ProviderManager(provider);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    
}