package com.example.demo.model.common.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonSerialize
public record TokenInfo(
        String accessToken,
        String refreshToken
) {
}
