package com.example.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.service.JwtService;
import com.example.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService service;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final var prefix = "Bearer ";
        final var isBearer = isBearerToken(request, prefix);

        if (!isBearer) {
            filterChain.doFilter(request, response);
            return;
        }

        final var jwt = extractJwtFromRequest(request, prefix);
        final var isValidate = service.validateJwtToken(jwt);

        if (isValidate) {
            setAuthentication(jwt, request);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isBearerToken(HttpServletRequest request, String prefix) {
        final var authorizationHeader = request.getHeader("Authorization");

        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(prefix);
    }

    private String extractJwtFromRequest(HttpServletRequest request, String prefix) {
        final var prefixLength = prefix.length();
        return request.getHeader("Authorization").substring(prefixLength);
    }

    private void setAuthentication(String jwt, HttpServletRequest request) {
        final var username = service.getUserName(jwt);
        final var user = userDetailsService.loadUserByUsername(username);
        final var authorities = user.getAuthorities();
        final var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        final var details = new WebAuthenticationDetailsSource().buildDetails(request);
        
        authentication.setDetails(details);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
