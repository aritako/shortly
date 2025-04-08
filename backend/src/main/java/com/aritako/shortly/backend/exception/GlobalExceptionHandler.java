package com.aritako.shortly.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

import java.time.ZonedDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> handleGlobalException(
    RuntimeException ex,
    HttpServletRequest request
  ){
    Map<String, Object> response = Map.of(
      "timestamp", ZonedDateTime.now()  ,
      "status", HttpStatus.BAD_REQUEST.value(),
      "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
      "message", ex.getMessage(),
      "path", request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

}
