package com.saxakiil.taskmanager.handler;

import com.saxakiil.taskmanager.dto.error.ErrorDto;
import com.saxakiil.taskmanager.exception.RecordNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@RestControllerAdvice
public class RecordNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<ErrorDto> handlerRecordNotFound(RecordNotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), ((ServletWebRequest) request).getRequest().getRequestURI(),
                e.getMessage(), LocalDateTime.now().toString()), HttpStatus.NOT_FOUND);
    }
}
