package com.consti.security.GlobalControllers;

import io.jsonwebtoken.JwtException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {


        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<String> handleNotFoundException(IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(JwtException.class)
        public ResponseEntity<String> handleJWTException(JwtException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> canNotAlterException(IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<String> notReadableException(HttpMessageNotReadableException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

}

