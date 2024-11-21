package com.example.demo.web.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.web.exception.domain.CredentialNotMatchException;
import com.example.demo.web.exception.domain.ExceptionStatus;
import com.example.demo.web.exception.domain.TokenNotFoundException;
import com.example.demo.web.exception.domain.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class RouteExceptionHandler {

    @ExceptionHandler(CredentialNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotMatchException(CredentialNotMatchException e) {

        ExceptionResponse response = new ExceptionResponse(
                new Date().toString(),
                ExceptionStatus.CREDENTIAL_NOT_MATCH,
                e.getMessage(),
                e.getMessage());

        log.error(response.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleTokenExpiredException(TokenExpiredException e) {

        ExceptionResponse response = new ExceptionResponse(
                new Date().toString(),
                ExceptionStatus.TOKEN_EXPIRED,
                "JWT is expired. expiredOn: " + e.getExpiredOn().toString(),
                e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTokenNotFoundException(TokenNotFoundException e) {

        ExceptionResponse response = new ExceptionResponse(
                new Date().toString(),
                ExceptionStatus.TOKEN_NOT_FOUND,
                "Reissue Token is not available.",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ExceptionResponse> handleJWTVerificationException(JWTVerificationException e) {

        ExceptionResponse response = new ExceptionResponse(
                new Date().toString(),
                ExceptionStatus.JWT_AUTH_EXCEPTION,
                "JWT Verify Authentication Exception raised.",
                e.getMessage());

        log.error(response.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e) {

        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(new Date().toString())
                .message("인증 예외가 발생하였습니다.")
                .detail(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
