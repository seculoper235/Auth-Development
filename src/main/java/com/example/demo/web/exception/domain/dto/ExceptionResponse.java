package com.example.demo.web.exception.domain.dto;

import com.example.demo.web.exception.domain.ExceptionStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonSerialize
@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponse {

    private final String timestamp;

    private final String code;

    private final String message;

    private final String detail;

    public ExceptionResponse(String timestamp, ExceptionStatus status, String message, String detail) {

        this.timestamp = timestamp;
        this.code = status.getCode();
        this.message = message;
        this.detail = detail;
    }
}
