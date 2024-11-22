package com.example.demo.service.token;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonSerialize
public record TokenInfo(
        String accessToken,
        String refreshToken
) {
}
