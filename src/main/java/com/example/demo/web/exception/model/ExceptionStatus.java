package com.example.demo.web.exception.model;

import lombok.Getter;

@Getter
public enum ExceptionStatus {
    ENTITY_NOT_FOUND("ENF"),
    CREDENTIAL_NOT_MATCH("CNM"),
    JWT_AUTH_EXCEPTION("TK001"),
    TOKEN_EXPIRED("TK002"),
    TOKEN_INVALID("TK003");

    private final String code;

    ExceptionStatus(String code) {
        this.code = code;
    }
}
