package com.demo.bad_data_batch.controller;

import com.demo.bad_data_batch.exception.InvalidRequestException;
import com.demo.bad_data_batch.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadRequest(final InvalidRequestException exception) {
        log.error("handleBadRequest {}", exception.getMessage());
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFound(final NotFoundException exception) {
        log.info("handleNotFound {}", exception.getMessage());
        return new ErrorMessage(exception.getMessage());
    }
}
