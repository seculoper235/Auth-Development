package com.example.demo.web.controller.auth;

import com.example.demo.model.common.security.TokenInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record LoginResponse(
        Long id,
        String email,
        TokenInfo tokenInfo) {}
