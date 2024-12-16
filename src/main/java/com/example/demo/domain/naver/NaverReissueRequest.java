package com.example.demo.domain.naver;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record NaverReissueRequest(
        String grant_type,
        String client_id,
        String client_secret,
        String refresh_token
) {
}
