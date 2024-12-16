package com.example.demo.service.auth;

import com.example.demo.domain.naver.NaverClient;
import com.example.demo.domain.naver.NaverReissueRequest;
import com.example.demo.domain.naver.NaverUnlinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

@RequiredArgsConstructor
public class NaverService extends TokenClientService {
    private final ClientRegistration registration;
    private final NaverClient naverClient;

    public String reissue(String refreshToken) {
        NaverReissueRequest naverReissueRequest = new NaverReissueRequest(
                "refresh_token",
                registration.getClientId(),
                registration.getClientSecret(),
                refreshToken
        );

        return naverClient.reissueToken(naverReissueRequest).access_token();
    }

    public void unlink(String token) {
        String accessToken = this.reissue(token);

        NaverUnlinkRequest naverUnlinkRequest = new NaverUnlinkRequest(
                "delete",
                registration.getClientId(),
                registration.getClientSecret(),
                accessToken
        );

        naverClient.unlink(naverUnlinkRequest);
    }
}
