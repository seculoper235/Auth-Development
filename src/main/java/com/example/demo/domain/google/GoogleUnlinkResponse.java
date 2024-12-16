package com.example.demo.domain.google;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record GoogleUnlinkResponse(
        String error,
        String error_description
) {
}
