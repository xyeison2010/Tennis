package com.leo.tennis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 * <p>Exception handler customizado</p>
 * Este componente permite declarar excepciones personalizadas, con el HttpStatus particular para cada una de esas
 * excepciones, estas pueden ser arrojadas utilizando throw new NoSuchElementException("Mensaje"); por ej
 */
//permite controlar las excepcion del responseentity
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    //en caso no encontrar devuelve return algo no encontrado
    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<RuntimeException> handle(NoSuchElementException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(),ex);
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<RuntimeException> handle(IllegalArgumentException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(),ex);
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<RuntimeException> handle(RuntimeException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(),ex);
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}