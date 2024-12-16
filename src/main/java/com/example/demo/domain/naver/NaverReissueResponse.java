package com.example.demo.domain.naver;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record NaverReissueResponse(
        String access_token,
        String token_type,
        String expired_in
) {
}
