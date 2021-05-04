package com.example.demo.services;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                              HttpHeaders headers,
                                                              HttpStatus status,
                                                              WebRequest request) {
        String error = "Malformed Json request";
        return build(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    public ResponseEntity<Object> handleHttpBadRequest(String message, Throwable throwable) {
        return build(new ApiError(HttpStatus.BAD_REQUEST, message, throwable));
    }

    private ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
