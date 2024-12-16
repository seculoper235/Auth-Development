package com.example.demo.domain.naver;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record NaverUnlinkResponse(
        String access_token,
        String result
) {
}
