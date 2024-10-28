package com.example.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ControllerAspect {

    private static final String CONTROLLER_METHODS = "controllerMethods()";
    private static final String POINTCUT_CONTROLLER = "execution(* com.example.controller.*Controller.*(..))";

    @Pointcut(POINTCUT_CONTROLLER)
    public void controllerMethods() {
        // Метод для определения пути к контроллерам
    }

    @Before(CONTROLLER_METHODS)
    public void loggingBefore() {
        final var servlet = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final var request = servlet.getRequest();
        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();

        log.info("Начата обработка запроса. Пользователь: {}. URL: {}", username, url);
    }

    @AfterReturning(CONTROLLER_METHODS)
    public void loggingAfter() {
        final var servlet = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final var request = servlet.getRequest();
        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();

        log.info("Обработка завершена успешно. Пользователь: {}. URL: {}", username, url);
    }

    @AfterThrowing(pointcut = CONTROLLER_METHODS, throwing = "exception")
    public void loggingAfterThrowing(Exception exception) {
        final var servlet = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final var request = servlet.getRequest();
        final var url = request.getRequestURI();
        final var username = request.getRemoteUser();
        final var message = exception.getMessage();

        log.error("Произошла ошибка. Пользователь: {}. URL: {}", username, url);
        log.error("Ошибка: {}", message);
        log.error("StackTrace: ", exception);
    }

}
