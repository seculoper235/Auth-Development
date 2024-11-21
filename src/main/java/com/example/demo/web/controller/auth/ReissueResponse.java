package com.example.demo.web.controller.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ReissueResponse(String accessToken) {}
