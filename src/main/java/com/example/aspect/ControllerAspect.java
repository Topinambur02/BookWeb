package com.example.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(* com.example.controller.*Controller.*(..))")
    public void controllerMethods() {
        
    }

    @Before("controllerMethods()")
    public void loggingBefore() {
        final var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();

        logger.info("Начата обработка запроса. Пользователь: {}. URL: {}", username, url);

    }

    @AfterReturning("controllerMethods()")
    public void loggingAfter() {
        final var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();

        logger.info("Обработка завершена успешно. Пользователь: {}. URL: {}", username, url);
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void loggingAfterThrowing(Exception exception) {
        final var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();
        final var message = exception.getMessage();

        logger.error("Произошла ошибка. Пользователь: {}. URL: {}", username, url);
        logger.error("Ошибка: {}", message);
        logger.error("StackTrace: ", exception);

    }

}
