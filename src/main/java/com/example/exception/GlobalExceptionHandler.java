package com.example.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler implements AuthenticationEntryPoint {

    // 400: Bad request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(HttpMessageNotReadableException exception,
            HttpServletRequest request, HttpServletResponse response) {
        return handleException(request, response, exception);
    }

    // 404: Not found
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NoHandlerFoundException exception, HttpServletRequest request,
            HttpServletResponse response) {
        return handleException(request, response, exception);
    }

    // 405: Method not allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request, HttpServletResponse response) {
        return handleException(request, response, exception);
    }

    // 500: Internal server error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerException(Exception exception, HttpServletRequest request,
            HttpServletResponse response) {
        return handleException(request, response, exception);
    }

    // 401: Unauthorized
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {
        final var objectMapper = new ObjectMapper();
        final var errorResponse = handleException(request, response, authenticationException);

        final var jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    private ErrorResponse handleException(HttpServletRequest request, HttpServletResponse response,
            Exception exception) {
        final var currentTime = LocalDateTime.now();
        final var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
        final var formattedDate = currentTime.format(formatter);

        final var url = request.getRequestURI();
        final var message = exception.getMessage();
        final var user = SecurityContextHolder.getContext().getAuthentication();

        final var isAuthenticated = user != null && user.isAuthenticated();

        if (!isAuthenticated) {
            return new ErrorResponse(message, formattedDate, url, null);
        }

        final var username = user.getName();

        return new ErrorResponse(message, formattedDate, url, username);
    }
}