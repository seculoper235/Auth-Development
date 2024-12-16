package com.example.demo.domain.naver;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@Component
@HttpExchange
public interface NaverClient {

    @PostExchange("/oauth2/token")
    NaverReissueResponse reissueToken(
            @RequestBody NaverReissueRequest reissueRequest);

    @PostExchange("/oauth2/token")
    NaverUnlinkResponse unlink(
            @RequestBody NaverUnlinkRequest reissueRequest);
}
