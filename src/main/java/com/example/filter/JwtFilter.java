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
        final var authorizationHeader = request.getHeader("Authorization");
        final var prefix = "Bearer ";
        final var prefixLength = prefix.length();
        final var isBearer = StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ");

        if (!isBearer) {
            filterChain.doFilter(request, response);
            return;
        }

        final var jwt = authorizationHeader.substring(prefixLength);
        final var isValidate = service.validateJwtToken(jwt);

        if (isValidate) {
            final var username = service.getUserName(jwt);
            final var user = userDetailsService.loadUserByUsername(username);
            final var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            final var details = new WebAuthenticationDetailsSource().buildDetails(request);

            authentication.setDetails(details);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
