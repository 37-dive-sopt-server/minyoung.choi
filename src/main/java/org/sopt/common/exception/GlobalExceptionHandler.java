package org.sopt.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", e.getErrorCode().getStatus().value(),
                        "error", e.getErrorCode().name(),
                        "message", e.getErrorCode().getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        return ResponseEntity.status(500)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 500,
                        "error", "INTERNAL_SERVER_ERROR",
                        "message", e.getMessage()
                ));
    }
}

