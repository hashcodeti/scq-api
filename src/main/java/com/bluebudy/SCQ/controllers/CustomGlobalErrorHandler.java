package com.bluebudy.SCQ.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bluebudy.SCQ.dtos.FormErrorDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalErrorHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource msgSource;


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex,HttpServletResponse response) throws IOException {    
        return ResponseEntity.badRequest().body(List.of(new FormErrorDto("Duplicado" , "Cadastro duplicado") ));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FormErrorDto> formErrors = new LinkedList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.stream().forEach(e -> {
            String msg = msgSource.getMessage(e, LocaleContextHolder.getLocale());
            formErrors.add(new FormErrorDto(e.getField(), msg));
        });
        return ResponseEntity.badRequest().body(formErrors);
    }

}
