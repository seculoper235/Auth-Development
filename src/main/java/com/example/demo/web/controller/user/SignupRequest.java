package com.example.demo.web.controller.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record SignupRequest(
        String name,
        String email,
        String password
) {
}
