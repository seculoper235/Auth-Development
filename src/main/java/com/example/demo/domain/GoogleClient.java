package com.example.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@JsonSerialize
record GoogleReissueRequest(
        String client_id,
        String client_secret,
        String refresh_token,
        String grant_type
) {
}

@JsonSerialize
record GoogleReissueResponse(
        String access_token,
        String expires_in,
        String scope,
        String token_type
) {
}

@HttpExchange
public interface GoogleClient {

    @PostExchange("/token")
    GoogleReissueResponse reissue(
            @RequestBody GoogleReissueRequest reissueRequest);

    @PostExchange("/revoke")
    void unlink(
            @RequestBody String token);
}
