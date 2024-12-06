package com.example.demo.web.security.config;

import com.example.demo.model.common.auth.SnsType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, RuntimeException {
        String type = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        OAuthInfo oAuthInfo = OAuthInfoProvider.getOAuthInfo(SnsType.valueOf(type.toUpperCase()), oAuth2User);

        response.sendRedirect("http://localhost:5173/?uid=" + oAuthInfo.getId() + "&type=" + oAuthInfo.getRegistrationId());
    }
}
