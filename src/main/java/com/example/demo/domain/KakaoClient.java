package com.example.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@JsonSerialize
record KakaoUnlinkRequest(
        String target_id_type,
        String target_id
) {
}

@HttpExchange
public interface KakaoClient {

    @PostExchange("/v1/user/unlink")
    String unlink(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String adminKey,
            @RequestBody KakaoUnlinkRequest unlinkRequest);
}
