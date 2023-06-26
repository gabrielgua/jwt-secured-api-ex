package com.gabriel.jwtexample.Jwt.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        var status = HttpStatus.UNAUTHORIZED;
        var detail = "Invalid email or password";
        var type = ErrorType.BAD_CREDENTIALS;

        var error = createErrorBuilder(status, type, detail).build();
        return ResponseEntity.status(status).body(error);
    }

    private StandardError.StandardErrorBuilder createErrorBuilder(HttpStatus status, ErrorType type, String detail) {
        return StandardError.builder()
                .status(status.value())
                .type(type.getUrl())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }
}
