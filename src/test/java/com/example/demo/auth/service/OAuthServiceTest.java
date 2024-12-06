package com.example.demo.auth.service;

import com.example.demo.config.UnitTest;
import com.example.demo.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public class OAuthServiceTest extends UnitTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private OAuth2UserRequest oAuth2UserRequest;

    @Mock
    private DefaultOAuth2UserService oAuth2UserService;

    @Test
    @DisplayName("")
    void signup_user_success_return_user_api() {
    }
}
