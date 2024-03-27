package com.consti.security.GlobalControllers;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleNotFoundException(IllegalStateException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}

