package com.example.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

@JsonSerialize
record NaverReissueRequest(
        String grant_type,
        String client_id,
        String client_secret,
        String refresh_token
) {
}

@JsonSerialize
record NaverReissueResponse(
        String access_token,
        String token_type,
        String expired_in
) {
}

@JsonSerialize
record NaverUnlinkRequest(
        String grant_type,
        String client_id,
        String client_secret,
        String access_token
) {
}

@JsonSerialize
record NaverUnlinkResponse(
        String access_token,
        String result
) {
}

public interface NaverClient {

    @PostExchange("/oauth2/token")
    NaverReissueResponse reissueToken(
            @RequestBody NaverReissueRequest reissueRequest);

    @PostExchange("/oauth2/token")
    NaverUnlinkResponse unlink(
            @RequestBody NaverUnlinkRequest reissueRequest);
}
