package com.example.demo.domain.kakao;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@Component
@HttpExchange
public interface KakaoClient {

    @PostExchange("/v1/user/unlink")
    String unlink(
            @RequestHeader("KakaoAK") String adminKey,
            @RequestBody KakaoUnlinkRequest unlinkRequest);
}
