package com.example.demo.auth.service;

import com.example.demo.config.ServiceTestEnv;
import com.example.demo.domain.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TokenServiceTest extends ServiceTestEnv {
    @InjectMocks
    private TokenService tokenService;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("인증 정보를 받아 토큰 정보를 반환한다")
    void user_principal_create_token_info() {
        //
    }

    @Test
    @DisplayName("토큰 재발급 시, 리프레시 토큰으로 어세스 토큰을 재발급한다")
    void refresh_token_reissue_access_token() {
        //
    }

    @Test
    @DisplayName("토큰 재발급 시, 유효하지 않은 리프레시 토큰을 받으면 에러를 던진다")
    void no_valid_refresh_token_reissue_throw_TokenNotFoundException() {
        //
    }

    @Test
    @DisplayName("유효한 어세스 토큰을 받으면 인증 정보를 얻어낸다")
    void vaid_access_token_create_user_principal() {
        //
    }
}
