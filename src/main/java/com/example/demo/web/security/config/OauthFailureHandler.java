package com.example.demo.web.security.config;

import com.example.demo.common.http.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OauthFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, RuntimeException {
        String redirectUrl = findRedirectUrl(request, response);

        logger.error(exception.getMessage(), exception);

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        response.sendRedirect(targetUrl);
    }

    public String findRedirectUrl(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.removeCookie(response, "client_redirect_url");

        return CookieUtil.getCookie(request, "client_redirect_url");
    }
}
