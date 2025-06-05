package com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.exception;

import com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
            CustomResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(false)
                .message(ex.getMessage())
                .data(null)
                .build()
        );
    }
}