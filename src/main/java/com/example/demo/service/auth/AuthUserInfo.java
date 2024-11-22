package com.example.demo.service.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record AuthUserInfo(
        Long id,
        String email
) {}
