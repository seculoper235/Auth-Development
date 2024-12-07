package com.example.demo.service.auth;

import com.example.demo.model.common.auth.SnsType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record SnsAccountInfo(
        Long id,
        String uid,
        SnsType type
) {
}
