package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Around("execution(* com.example.controller.*Controller.*(..))")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();

        logger.info("Начата обработка запроса. Пользователь: {}. URL: {}", username, url);

        Object result;

        try {
            result = proceedingJoinPoint.proceed();

            logger.info("Завершена обработка запроса.");

        }

        catch (Throwable exception) {
            final var message = exception.getMessage();

            logger.error("Возникла ошибка при обработке запроса.");
            logger.error("Ошибка: {}", message);
            logger.error("StackTrace: ", exception);

            throw exception;
        }

        return result;

    }

}
