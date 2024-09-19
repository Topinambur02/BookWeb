package com.example.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security.JwtUtils;
import com.example.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils utils;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        final var authorizationHeader = request.getHeader("Authorization");
        final var prefix = "Bearer ";
        final var prefixLength = prefix.length();
        final var isBearer = StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ");

        if (!isBearer) {
            filterChain.doFilter(request, response);
            return;
        }

        final var jwt = authorizationHeader.substring(prefixLength);
        final var isValidate = utils.validateJwtToken(jwt);

        if (isValidate) {

            final var username = utils.getUserName(jwt);
            final var user = userService.loadUserByUsername(username);

            final var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            final var details = new WebAuthenticationDetailsSource().buildDetails(request);

            authentication.setDetails(details);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
