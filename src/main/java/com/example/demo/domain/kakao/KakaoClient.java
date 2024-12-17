package com.example.demo.domain.kakao;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface KakaoClient {

    @PostExchange("/v1/user/unlink")
    void unlink(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String adminKey,
            @RequestBody KakaoUnlinkRequest unlinkRequest);
}
