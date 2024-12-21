package com.example.demo.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final OAuth2AuthorizedClient authorizedClient;

    public OAuthService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.authorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(type, request.uid());
    }

    public String getRegistrationId() {
        return "";
    }
}
