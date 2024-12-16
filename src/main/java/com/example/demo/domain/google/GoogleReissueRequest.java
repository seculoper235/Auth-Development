package com.example.demo.domain.google;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record GoogleReissueRequest(
        String client_id,
        String client_secret,
        String refresh_token,
        String grant_type
) {
}
