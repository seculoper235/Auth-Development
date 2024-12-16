package com.example.demo.domain.naver;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record NaverUnlinkRequest(
        String grant_type,
        String client_id,
        String client_secret,
        String access_token
) {
}
