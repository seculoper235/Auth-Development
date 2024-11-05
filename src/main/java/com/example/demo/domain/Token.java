package com.example.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonSerialize
public record Token(
        String accessToken,
        String refreshToken
) {
}
