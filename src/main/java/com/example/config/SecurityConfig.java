package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.exception.GlobalExceptionHandler;
import com.example.filter.JwtFilter;
import com.example.security.JwtUtils;
import com.example.service.UserService;
import com.example.wrapper.DaoAuthenticationProviderWrapper;
import com.example.wrapper.JwtFilterWrapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils utils;
    private final UserService service;
    private final GlobalExceptionHandler exceptionHandler;

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/users/signup", "/users/signin", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .anonymous(anonymous -> anonymous.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(exceptionHandler))
                .formLogin(login -> login
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/books", true))
                .addFilterBefore(jwtFilter(utils, service), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    JwtFilter jwtFilter(JwtUtils utils, UserService service) {
        return JwtFilterWrapper
                .builder()
                .jwtUtils(utils)
                .userService(service)
                .build();
    }

    @Bean
    BCryptPasswordEncoder BCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        return DaoAuthenticationProviderWrapper
                .builder()
                .userDetailsService(userDetailsService)
                .passwordEncoder(BCryptPasswordEncoder())
                .build();
    }

}
