package com.example.demo.web.security.config;

import com.example.demo.common.http.CookieUtil;
import com.example.demo.model.common.auth.SnsType;
import com.example.demo.web.security.oauth.OAuthInfo;
import com.example.demo.web.security.oauth.OAuthInfoProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OauthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, RuntimeException {
        String type = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String redirectUrl = findRedirectUrl(request, response);

        OAuthInfo oAuthInfo = OAuthInfoProvider.getOAuthInfo(SnsType.valueOf(type.toUpperCase()), oAuth2User);

        response.sendRedirect(redirectUrl + "?uid=" + oAuthInfo.getId() + "&type=" + oAuthInfo.getRegistrationId());
    }

    public String findRedirectUrl(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.removeCookie(response, "client_redirect_url");

        return CookieUtil.getCookie(request, "client_redirect_url");
    }
}
