package com.saxakiil.taskmanager.handler;

import com.saxakiil.taskmanager.dto.error.FieldError;
import com.saxakiil.taskmanager.dto.error.ValidateFieldsError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ValidateFieldsError(ex.getFieldErrors().stream()
                .map(element -> new FieldError(element.getField(), element.getCode()))
                .toList()), HttpStatus.BAD_REQUEST);
    }
}