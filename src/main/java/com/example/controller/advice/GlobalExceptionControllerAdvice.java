package com.example.controller.advice;

import java.util.Optional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.dto.rest.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionControllerAdvice implements AuthenticationEntryPoint {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 400: Bad request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handleBadRequestException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return handleException(request, exception);
    }

    // 404: Not found
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorDto handleNotFoundException(NoHandlerFoundException exception, HttpServletRequest request) {
        return handleException(request, exception);
    }

    // 405: Method not allowed
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorDto handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        return handleException(request, exception);
    }

    // 500: Internal server error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleInternalServerException(Exception exception, HttpServletRequest request) {
        return handleException(request, exception);
    }

    // 401: Unauthorized
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {
        final var errorResponse = handleException(request, authenticationException);
        final var jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
    }

    private ErrorDto handleException(HttpServletRequest request, Exception exception) {
        final var currentTime = LocalDateTime.now();
        final var formattedDate = currentTime.format(DATETIME_FORMATTER);
        final var url = request.getRequestURI();
        final var message = exception.getMessage();
        final var user = SecurityContextHolder.getContext().getAuthentication();
        final var username = Optional.ofNullable(user).map(Authentication::getName).orElse(null);

        return ErrorDto
                .builder()
                .message(message)
                .date(formattedDate)
                .url(url)
                .username(username)
                .build();
    }
    
}