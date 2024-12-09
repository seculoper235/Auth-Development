package com.example.demo.web.exception.model;

import lombok.Getter;

@Getter
public enum ExceptionStatus {
    ENTITY_NOT_FOUND("ENF"),
    ENTITY_DUPLICATED("EDC"),
    CREDENTIAL_NOT_MATCH("CNM"),
    AUTHORIZED_NOT_FOUND("ANF"),
    JWT_AUTH_EXCEPTION("TK001"),
    TOKEN_EXPIRED("TK002"),
    TOKEN_INVALID("TK003");

    private final String code;

    ExceptionStatus(String code) {
        this.code = code;
    }
}
